# Simple performance test: Exposed vs plain JDBC vs Hibernate

# With HikariCP
### 1
- plain jdbc autoCommit=false > insert time: 782ms
- plain jdbc autoCommit=false > select time: 286ms
- plain jdbc autoCommit=true > insert time: 611ms
- plain jdbc autoCommit=true > select time: 479ms
- exposed dsl > insert time: 1612ms
- exposed dsl > select time: 1010ms
- exposed transaction only > insert time: 544ms
- exposed transaction only > select time: 633ms
- exposed dao > insert time: 1750ms
- exposed dao > select time: 977ms
- hibernate dao > insert time: 2193ms
- hibernate dao > select time: 801ms
___
### 2
- plain jdbc autoCommit=false > insert time: 363ms
- plain jdbc autoCommit=false > select time: 198ms
- plain jdbc autoCommit=true > insert time: 476ms
- plain jdbc autoCommit=true > select time: 432ms
- exposed dsl > insert time: 1543ms
- exposed dsl > select time: 853ms
- exposed transaction only > insert time: 566ms
- exposed transaction only > select time: 595ms
- exposed dao > insert time: 1640ms
- exposed dao > select time: 1023ms
- hibernate dao > insert time: 2077ms
- hibernate dao > select time: 782ms
___
### 3
- plain jdbc autoCommit=false > insert time: 427ms
- plain jdbc autoCommit=false > select time: 220ms
- plain jdbc autoCommit=true > insert time: 432ms
- plain jdbc autoCommit=true > select time: 390ms
- exposed dsl > insert time: 1394ms
- exposed dsl > select time: 1011ms
- exposed transaction only > insert time: 519ms
- exposed transaction only > select time: 667ms
- exposed dao > insert time: 1493ms
- exposed dao > select time: 827ms
- hibernate dao > insert time: 2170ms
- hibernate dao > select time: 786ms
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