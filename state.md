# State of APS examples as of March 12th, 2023

| Example                        | Dynamic scheduling | Old Scheduler | Old Code Generation | SCC Scheduler | New Code generation |
|--------------------------------|--------------------|---------------|---------------------|---------------|---------------------|
| `first.aps`                    | Pass               | Pass          | Fail                | Pass          | Pass                |
| `follow.aps`                   | Pass               | Pass          | Fail                | Pass          | Pass                |
| `nullable.aps`                 | Pass               | Pass          | Fail                | Pass          | Pass                |
| `simple-binding.aps`           | Fail               | Pass          | Fail                | Pass          | Fail                |
| `simple-binding1.aps`          | Fail               | Pass          | Fail                | Pass          | Fail                |
| `simple-binding2.aps`          | Fail               | Pass          | Fail                | Pass          | Fail                |
| `simple-binding3.aps`          | Fail               | Pass          | Pass                | Pass          | Pass                |
| `simple-oag.aps`               | Pass               | Pass          | Pass                | Pass          | Pass                |
| `simple-coag.aps`              | Fail               | Pass          | Pass                | Fail          | -                   |
| `simple-snc.aps`               | Fail               | Fail          | -                   | Fail          | -                   |
| `simple-syn.aps`               | Pass               | Pass          | Pass                | Pass          | Pass                |
| `local-fiber-cycle.aps`        | Fail               | Pass          | Pass                | Pass          | Pass                |
| `broad-fiber-cycle.aps`        | Fail               | Pass          | Fail                | Pass          | Pass                |
| `below-fiber-cycle.aps`        | Fail               | Pass          | Pass                | Pass          | Pass                |
| `below-single-fiber-cycle.aps` | Fail               | Pass          | Fail                | Pass          | Fail                |
| `cycle-series.aps`             | Fail               | Fail          | -                   | Pass          | Fail                |
| `multi-series.aps`             | Fail               | Fail          | -                   | Pass          | Fail                |
| `cycle.aps`                    | Fail               | Fail          | -                   | Fail          | -                   |
| `nested-cycles.aps`            | Pass               | Pass          | Fail                | Pass          | Pass                |
| `cool-noinherit-semant.aps`    | Pass               | Pass          | Pass                | Pass          | Pass                |
| `cool-circular-semant.aps`     | Fail               | Pass          | Fail                | Pass          | Pass                |
| `cool-semant.aps`              | Fail               | Pass          | Fail                | Pass          | Fail                |
| `cool-dynamic-semant.aps`      | Pass               | Pass          | Fail                | Pass          | Fail                |

---

## Descriptions


`first.aps`

- Old static code generation fails to find where to place child visit call
```
first.aps:42:Warning: used inherited attributes of children
```

---

`follow.aps`

- Old static code generations failed in evaluation because of:
```
java.lang.NullPointerException
```

---

`nullable.aps`

- Old static code generation fails to find where to place child visit call
```
nullable.aps:37:Warning: used inherited attributes of children
```

---

`simple-binding.aps`

- Dynamic + both Old and new static code generation fails because complex list match is not supported
```
match {...,?e:EntityRef if e.entity_name=name,...}
```

---

`simple-binding1.aps`

- Dynamic + both Old and new static code generation fails because complex list match is not supported
```
match {...,?e:EntityRef if e.entity_name=name,...}
```

---

`simple-binding2.aps`

- Dynamic + both Old and new static code generation fails because procedure not supported (i.e. lookup procedure does not code generate)

---

`simple-binding3.aps`

- Dynamic scheduling failed because:
```
Evaluation$UndefinedAttributeException: undefined attribute: entity().entity_name
```

---

`simple-coag.aps`

- Dynamic scheduling failed because:
```
undefined attribute: decl(AtpA8g,string_type()).bs
```

- New SCC scheduler fails because it cannot handle conditional cycles and throws an error

---

`simple-snc.aps`

- Dynamic scheduling failed because:
```
Evaluation$UndefinedAttributeException: undefined attribute: strconstant(2).s2
```

- Both old and new static schedulers fail because:
```
simple-snc.aps:26:Unable to handle dependency (23); Attribute grammar is not DNC
```

---

`local-fiber-cycle.aps`

- Dynamic scheduling fails for all AST depths:
```
Evaluation$UndefinedAttributeException: undefined attribute: context(0).ptr1
```

---

`broad-fiber-cycle.aps`

- Dynamic scheduling fails for all AST depths:
```
Evaluation$UndefinedAttributeException: undefined attribute: context(0).ptr2
```

- Old static code generations fails to find where child visit should be placed: 
```
broad-fiber-cycle.aps:20:Warning: used inherited attributes of children
fatal error: stopping
```

---

`below-fiber-cycle.aps`

- Dynamic scheduling fails for all AST depths:
```
Evaluation$UndefinedAttributeException: undefined attribute: context(0).ptr1
```

---

`below-single-fiber-cycle.aps`

- Dynamic scheduling fails for all AST depths:
```
Evaluation$UndefinedAttributeException: undefined attribute: context(0).ptr
```

- Both Old and new static code generations throws stackoverflow error:
```
Exception in thread "main" java.lang.StackOverflowError
	at M__basic_8$$Lambda$133/0x0000000840165040.<init>(Unknown Source)
	at M__basic_8$$Lambda$133/0x0000000840165040.get$Lambda(Unknown Source)
	at M__basic_8.<init>(basic.handcode.scala:398)
	at M_BELOW_SINGLE_FIBER_CYCLE.f_index_scope(tiny-impl.scala:117)
	at M_BELOW_SINGLE_FIBER_CYCLE.$anonfun$v_index_scope$1(tiny-impl.scala:111)
	at M_BELOW_SINGLE_FIBER_CYCLE.$anonfun$v_index_scope$1$adapted(tiny-impl.scala:111)
	at M_BELOW_SINGLE_FIBER_CYCLE.f_index_scope(tiny-impl.scala:141)
	at M_BELOW_SINGLE_FIBER_CYCLE.$anonfun$v_index_scope$1(tiny-impl.scala:111)
	at M_BELOW_SINGLE_FIBER_CYCLE.$anonfun$v_index_scope$1$adapted(tiny-impl.scala:111)
	at M_BELOW_SINGLE_FIBER_CYCLE.f_index_scope(tiny-impl.scala:141)
```

---

`cycle-series.aps`

- Dynamic scheduling fails because for-loop is not supported

- Old static scheduling fails:
```
cycle-series.aps:37:Cycle detected (7); Attribute grammar is not OAG
```

- New SCC scheduler fails: for-loop is not supported

---

`multi-cycle.aps`

- Dynamic scheduling fails because
```
Evaluation$CyclicAttributeException: cyclic attribute: non-monotonic leaf(1410914374).s2
```

- Old static scheduling fails:
```
multi-cycle.aps:39:Cycle detected (7); Attribute grammar is not OAG
```

- New SCC scheduler fails:
```
Evaluation$CyclicAttributeException: cyclic attribute: non-monotonic leaf(304649681).s2
```

---

`cycle.aps`

- Dynamic scheduling fails because
```
Evaluation$CyclicAttributeException: cyclic attribute: leaf(1315732640).leaf(1315732640).i
```

- Both Old and new static schedulers fails during analysis:
```
cycle.aps:19:Unable to handle dependency (23); Attribute grammar is not DNC
```

---

`nested-cycles.aps`

- Old static code generation fails in evaluation because of:
```
java.lang.NullPointerException
```

---

`cool-circular-semant.aps`

- Dynamic scheduling fails at evaluation
```
Evaluation$UndefinedAttributeException: undefined attribute: cool_class('Any,'/usr/local/lib/basic.cool).class_is_inheritable
```

- Old static scheduler fails at code generation, failing to find where to insert child visit call
```
cool-circular-semant.aps:325:Warning: used inherited attributes of children
```

---

`cool-semant.aps`

- Dynamic scheduling fails at evaluation
```
Evaluation$UndefinedAttributeException: undefined attribute: cool_class('Any,'/usr/local/lib/basic.cool).parent_class
```

- Old static scheduler fails during evaluation
```
Evaluation$UndefinedAttributeException: undefined attribute: class_contour('/usr/local/lib/basic.cool).class_contour('/usr/local/lib/basic.cool).enclosing
```  

- New static scheduler fails during evaluation
```
Exception in thread "main" Evaluation$UndefinedAttributeException: undefined attribute: class_contour('/usr/local/lib/basic.cool).class_contour('/usr/local/lib/basic.cool).enclosing
```

---

`cool-dynamic-semant.aps`

- Old static scheduler evaluation fails:
```
java.lang.NullPointerException
```

- New static scheduler fails during evaluation (this is a known `aps-fiber.c` issue where it doesn't record all dependencies):
```
Evaluation$UndefinedAttributeException: undefined attribute: method(false,'Any,none(),'Any,no_expr()).method(false,'Any,none(),'Any,no_expr()).cl
```

---