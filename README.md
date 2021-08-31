# Simple performance test: Exposed vs plain JDBC vs Hibernate

# With HikariCP
### 1
- plain jdbc autoCommit=false > insert time: 384ms
- plain jdbc autoCommit=false > select time: 265ms
- plain jdbc autoCommit=true > insert time: 445ms
- plain jdbc autoCommit=true > select time: 490ms
- exposed dsl > insert time: 1834ms
- exposed dsl > select time: 1428ms
- exposed transaction only > insert time: 620ms
- exposed transaction only > select time: 679ms
- exposed dao > insert time: 1782ms
- exposed dao > select time: 1211ms
- hibernate dao > insert time: 2200ms
- hibernate dao > select time: 744ms
___
### 2
- plain jdbc autoCommit=false > insert time: 332ms
- plain jdbc autoCommit=false > select time: 213ms
- plain jdbc autoCommit=true > insert time: 419ms
- plain jdbc autoCommit=true > select time: 468ms
- exposed dsl > insert time: 2381ms
- exposed dsl > select time: 1616ms
- exposed transaction only > insert time: 690ms
- exposed transaction only > select time: 775ms
- exposed dao > insert time: 1659ms
- exposed dao > select time: 1290ms
- hibernate dao > insert time: 2295ms
- hibernate dao > select time: 922ms
___
### 3
- plain jdbc autoCommit=false > insert time: 422ms
- plain jdbc autoCommit=false > select time: 234ms
- plain jdbc autoCommit=true > insert time: 438ms
- plain jdbc autoCommit=true > select time: 461ms
- exposed dsl > insert time: 1841ms
- exposed dsl > select time: 1265ms
- exposed transaction only > insert time: 547ms
- exposed transaction only > select time: 596ms
- exposed dao > insert time: 1469ms
- exposed dao > select time: 994ms
- hibernate dao > insert time: 2283ms
- hibernate dao > select time: 889ms
___
# Without HikariCP
### 1
- plain jdbc autoCommit=false > insert time: 319ms
- plain jdbc autoCommit=false > select time: 249ms
- plain jdbc autoCommit=true > insert time: 461ms
- plain jdbc autoCommit=true > select time: 451ms
- exposed dsl > insert time: 1703ms
- exposed dsl > select time: 1275ms
- exposed transaction only > insert time: 677ms
- exposed transaction only > select time: 676ms
- exposed dao > insert time: 1930ms
- exposed dao > select time: 1312ms
- hibernate dao > insert time: 2030ms
- hibernate dao > select time: 734ms
___
### 2
- plain jdbc autoCommit=false > insert time: 476ms
- plain jdbc autoCommit=false > select time: 255ms
- plain jdbc autoCommit=true > insert time: 637ms
- plain jdbc autoCommit=true > select time: 463ms
- exposed dsl > insert time: 1618ms
- exposed dsl > select time: 1118ms
- exposed transaction only > insert time: 587ms
- exposed transaction only > select time: 674ms
- exposed dao > insert time: 1735ms
- exposed dao > select time: 1256ms
- hibernate dao > insert time: 2085ms
- hibernate dao > select time: 733ms
___
### 3
- plain jdbc autoCommit=false > insert time: 461ms
- plain jdbc autoCommit=false > select time: 297ms
- plain jdbc autoCommit=true > insert time: 540ms
- plain jdbc autoCommit=true > select time: 433ms
- exposed dsl > insert time: 1640ms
- exposed dsl > select time: 1236ms
- exposed transaction only > insert time: 775ms
- exposed transaction only > select time: 641ms
- exposed dao > insert time: 1672ms
- exposed dao > select time: 1105ms
- hibernate dao > insert time: 2150ms
- hibernate dao > select time: 751ms