import java.util.concurrent.TimeUnit
import scala.util.Random

object SimpleSpec extends App {

  def time(func: () => Unit, str: String = ""): (Double, Double) = {
    Runtime.getRuntime.gc()
    val t0 = System.nanoTime()
    val re = func()
    val t1 = System.nanoTime()
    val seconds = TimeUnit.MILLISECONDS.convert(t1 - t0, TimeUnit.NANOSECONDS) / 1000.0
    val usage = (Runtime.getRuntime.totalMemory() - Runtime.getRuntime.freeMemory()) / (1024.0 * 1024.0)

    println(s"$str Elapsed time: " + seconds + " sec  with usage: " + usage)
    (seconds, usage)
  }

  private final def genVariableName(depth: Int): String = {
    (Random.alphanumeric take 100).mkString("")
  }

  // This is used for the width of the AST
  private val prob: Map[Int, Double] = Map(2 -> 0.5, 3 -> 0.3, 5 -> 0.2)

  private final def sample[A](dist: Map[A, Double]): A = {
        val p = Random.nextDouble()
        val it = dist.iterator
        var acc = 0.0
        while (it.hasNext) {
          val (item, itemProb) = it.next
          acc += itemProb
          if (acc >= p)
            return item // return so that we don't have to search through the whole distribution
        }
        sys.error(f"this should never happen") // needed so it will compile
  }

  private final def genDecls(depth: Int, m: M_SIMPLE) = {
    var prev = m.v_no_decls()
    var count = 1;

    if (depth > 0) {
      for (d <- 1 to sample(prob)) {
        val name = genVariableName(depth)

        val ty = if ((d + depth) % 2 == 0) m.v_string_type() else m.v_integer_type()

        prev = m.v_xcons_decls(prev, m.v_decl(name, ty))
        count = count + 2;
      }
    }

    prev
  }

  private final def genStmts(depth: Int, m: M_SIMPLE): m.T_Stmts = {
    var prev = m.v_no_stmts()

    if (depth > 0) {
      for (d <- 1 to sample(prob)) {
        val name = genVariableName(depth)

        val v = if ((d - depth) % 2 == 0) m.v_strconstant((d + depth).toString) else m.v_intconstant(d + depth)

        prev = m.v_xcons_stmts(prev, m.v_assign_stmt(m.v_variable(name), v))
      }

      for (_ <- 1 to sample(prob)) {
        val bs = genBlock(depth, m)

        prev = m.v_xcons_stmts(prev, m.v_block_stmt(bs))
      }
    }

    prev
  }

  private final def genBlock(depth: Int, m: M_SIMPLE): m.T_Block = {
    assert(depth > 0, "Depth parameter passed into genBlock should be greater than one.")

    val ds = genDecls(depth - 1, m);
    val ss = genStmts(depth - 1, m);

    m.v_block(ds.asInstanceOf[m.T_Decls], ss.asInstanceOf[m.T_Stmts])
  }

  private final def genProgram(depth: Int, m: M_SIMPLE) = {
    val bs = genBlock(depth, m)

    m.v_program(bs.asInstanceOf[m.T_Block])
  }

  final def build(depth: Int) = {
    val m = new M_SIMPLE(f"simple")
    val r = m.t_Result

    val root = genProgram(depth, m)

    m.finish()

    r
  }

  for (depth <- 2 to 5) {
    println(f"depth: $depth")
    val tree = build(depth)

    try {
      val m_SIMPLE = new M_SIMPLE_SYN("SimpleImpl", tree)
      time(() => m_SIMPLE.finish())
      println("Succeeded")
    } catch {
      case e: Exception =>
        println(f"Running static failed ${e.toString}")
    }
  }

}
