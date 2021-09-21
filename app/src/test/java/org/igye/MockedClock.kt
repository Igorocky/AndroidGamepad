package org.igye

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.atomic.AtomicReference

class MockedClock(
    private val zoneId:ZoneId = ZoneId.systemDefault(),
    private var currInstant: AtomicReference<Instant> = AtomicReference(Instant.now())
): Clock() {
    override fun getZone(): ZoneId {
        return ZoneId.systemDefault()
    }

    override fun withZone(zone: ZoneId): Clock {
        return MockedClock(
            zoneId = zone,
            currInstant = currInstant
        )
    }

    override fun instant(): Instant {
        return currInstant.get()
    }

    fun addMillis(millis: Long) {
        currInstant.set(currInstant.get().plusMillis(millis))
    }
}