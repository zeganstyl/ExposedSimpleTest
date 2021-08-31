# Simple performance test: Exposed vs plain JDBC vs Hibernate

### 1
- plain jdbc autoCommit=false > insert time: 422ms
- plain jdbc autoCommit=false > select time: 262ms
- plain jdbc autoCommit=true > insert time: 412ms
- plain jdbc autoCommit=true > select time: 435ms
- exposed dsl > insert time: 2090ms
- exposed dsl > select time: 1350ms
- exposed transaction only > insert time: 617ms
- exposed transaction only > select time: 585ms
- exposed dao > insert time: 1583ms
- exposed dao > select time: 1150ms
- hibernate dao > insert time: 2100ms
- hibernate dao > select time: 866ms
___
### 2
- plain jdbc autoCommit=false > insert time: 437ms
- plain jdbc autoCommit=false > select time: 207ms
- plain jdbc autoCommit=true > insert time: 429ms
- plain jdbc autoCommit=true > select time: 413ms
- exposed dsl > insert time: 1857ms
- exposed dsl > select time: 1121ms
- exposed transaction only > insert time: 596ms
- exposed transaction only > select time: 727ms
- exposed dao > insert time: 1579ms
- exposed dao > select time: 1094ms
- hibernate dao > insert time: 2120ms
- hibernate dao > select time: 903ms
___
### 3
- plain jdbc autoCommit=false > insert time: 345ms
- plain jdbc autoCommit=false > select time: 248ms
- plain jdbc autoCommit=true > insert time: 454ms
- plain jdbc autoCommit=true > select time: 476ms
- exposed dsl > insert time: 1830ms
- exposed dsl > select time: 1191ms
- exposed transaction only > insert time: 501ms
- exposed transaction only > select time: 553ms
- exposed dao > insert time: 1463ms
- exposed dao > select time: 1093ms
- hibernate dao > insert time: 2133ms
- hibernate dao > select time: 861ms