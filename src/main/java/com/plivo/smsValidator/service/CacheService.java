package com.plivo.smsValidator.service;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@ApplicationScoped
public class CacheService {
	@Inject
	private Logger log;

	private Cache<String, String> stopCache;
	private Cache<String, Integer> thresholdCache;

	@PostConstruct
	public void initialize() {
		stopCache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(4, TimeUnit.HOURS).build();
		resetThresholdCache();
	}

	public void addToStopCache(String from, String to) {
		log.info("from = " + from + ", to = " + to);
		stopCache.put(from + "@" + to, "1"); // Dummy value - it is not used any where
	}

	public boolean isPresentInStopCache(String from, String to) {
		log.info("from = " + from + ", to = " + to);
		return stopCache.getIfPresent(from + "@" + to) != null;
	}

	@Schedule(dayOfMonth="*", hour="0")
	public void resetThresholdCache() {
		thresholdCache = CacheBuilder.newBuilder().maximumSize(10000).build();
	}

	public boolean isWithinThreshold(String from) {
		log.info("from = " + from);
		Integer counter = thresholdCache.getIfPresent(from);
		if (counter == null) {
			thresholdCache.put(from, 1);
			return true;
		} else if (counter < 50) {
			thresholdCache.put(from, ++counter);
			return true;
		}
		return false;
	}
}