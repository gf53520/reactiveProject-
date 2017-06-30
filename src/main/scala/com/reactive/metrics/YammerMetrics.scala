package com.reactive.metrics

import com.yammer.metrics.Metrics
import com.yammer.metrics.core.{Histogram, Meter, Gauge, Timer}
import java.util.concurrent.TimeUnit

/**
  * Created by guifeng on 2017/6/26.
  */

// moitor actor state, 例如 响应时间, 当前正在处理的消息数等
trait YammerMetrics {

  def meter(name: String, eventType: String): Meter =
    Metrics.newMeter(getClass, name, eventType, TimeUnit.SECONDS)

  def gauge[T](name: String, metric: => T, scope: String = null): Gauge[T] =
    Metrics.newGauge(getClass, name, scope, new Gauge[T] {
      override def value(): T = metric
    })

  def histogram(name: String): Histogram = Metrics.newHistogram(getClass, name, true)

  def timer(name: String,
            durationUnit: TimeUnit = TimeUnit.NANOSECONDS,
            rateUnit: TimeUnit = TimeUnit.SECONDS): Timer =
    Metrics.newTimer(getClass, name, durationUnit, rateUnit)
}