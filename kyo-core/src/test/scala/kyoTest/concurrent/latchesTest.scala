package kyoTest.concurrent

import kyo.concurrent.fibers._
import kyo.concurrent.latches._
import kyo.core._
import kyo.ios._
import kyoTest.KyoTest

class latchesTest extends KyoTest {

  "countDown + await" in run {
    for {
      latch <- Latches(1)
      _     <- latch.release
      _     <- latch.await
    } yield ()
  }

  "countDown(2) + await" in run {
    for {
      latch <- Latches(2)
      _     <- latch.release
      _     <- latch.release
      _     <- latch.await
    } yield ()
  }

  "countDown + fibers + await" in run {
    for {
      latch <- Latches(1)
      _     <- Fibers.fork(latch.release)
      _     <- latch.await
    } yield ()
  }

  "countDown(2) + fibers + await" in run {
    for {
      latch <- Latches(2)
      _     <- Fibers.fork(latch.release, latch.release)
      _     <- latch.await
    } yield ()
  }
}
