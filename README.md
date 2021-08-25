# Simple performance test: Exposed vs plain JDBC vs Hibernate

# 1
- testNativeAutoCommitFalse > insert time: 355ms
- testNativeAutoCommitFalse > select time: 276ms
- testNativeAutoCommitTrue > insert time: 487ms
- testNativeAutoCommitTrue > select time: 449ms
- testExposedDsl > insert time: 3620ms
- testExposedDsl > select time: 2481ms
- testExposedTransactionOnly > insert time: 1367ms
- testExposedTransactionOnly > select time: 1444ms
- testExposedDao > insert time: 2529ms
- testExposedDao > select time: 1629ms
- hibernate dao > insert time: 2052ms
- hibernate dao > select time: 712ms

# 2
- testNativeAutoCommitFalse > insert time: 378ms
- testNativeAutoCommitFalse > select time: 244ms
- testNativeAutoCommitTrue > insert time: 427ms
- testNativeAutoCommitTrue > select time: 461ms
- testExposedDsl > insert time: 3189ms
- testExposedDsl > select time: 2402ms
- testExposedTransactionOnly > insert time: 1296ms
- testExposedTransactionOnly > select time: 1284ms
- testExposedDao > insert time: 2487ms
- testExposedDao > select time: 1639ms
- hibernate dao > insert time: 2253ms
- hibernate dao > select time: 798ms

# 3
- testNativeAutoCommitFalse > insert time: 392ms
- testNativeAutoCommitFalse > select time: 246ms
- testNativeAutoCommitTrue > insert time: 527ms
- testNativeAutoCommitTrue > select time: 505ms
- testExposedDsl > insert time: 3259ms
- testExposedDsl > select time: 2674ms
- testExposedTransactionOnly > insert time: 1452ms
- testExposedTransactionOnly > select time: 1429ms
- testExposedDao > insert time: 2788ms
- testExposedDao > select time: 1704ms
- hibernate dao > insert time: 2145ms
- hibernate dao > select time: 834ms