import java.util.concurrent.TimeUnit
import scala.util.Random

object TinySpec extends App {

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

  final def genLeaf(m: M_TINY): m.T_Wood = {
    m.v_leaf(math.abs(Random.nextInt(Int.MaxValue)))
  }

  final def genBranch(depth: Int, m: M_TINY): m.T_Wood = {
    m.v_branch(genWood(depth - 1, m), genWood(depth - 1, m))
  }

  final def genWood(depth: Int, m: M_TINY): m.T_Wood = {
    if (depth == 0) {
      genLeaf(m)
    } else {
      genBranch(depth, m)
    }
  }

  final def getRoot(depth: Int, m: M_TINY): m.T_Root = {
    m.v_root(genWood(depth - 1, m))
  }

  final def build(depth: Int) = {
    val m = new M_TINY(f"tiny")
    val r = m.t_Result

    val root = getRoot(depth, m)

    m.finish()

    r
  }

  for (depth <- 2 to 5) {
    println(f"depth: $depth")
    val tree = build(depth)

    try {
      val m_TINY = new M_THREE("TinyImpl", tree)
      time(() => m_TINY.finish())
      println("Succeeded")
    } catch {
      case e: Exception =>
        println(f"Running static failed ${e.toString}")
    }
  }

}
