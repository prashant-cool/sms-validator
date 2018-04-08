package com.plivo.smsValidator.test;

import org.junit.Assert;
import org.junit.Test;

import com.plivo.smsValidator.rest.InboundService;

// @RunWith(Arquillian.class)
public class InboundServiceTest {
	// @Deployment
	// public static Archive<?> createTestArchive() {
	// return ShrinkWrap.create(WebArchive.class, "sms-validator.war").addClasses(Resources.class)
	// .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
	// // Deploy our test datasource
	// .addAsWebInfResource("test-ds.xml");
	// }
	//
	// @Inject
	// private Logger log;

	@Test
	public void testIsStopCommand() {
		System.out.println("Started.");
		InboundService inboundService = new InboundService();
		Assert.assertTrue(inboundService.isStopCommand("STOP"));
		Assert.assertTrue(inboundService.isStopCommand("STOP\n"));
		Assert.assertTrue(inboundService.isStopCommand("STOP\r"));
		Assert.assertTrue(inboundService.isStopCommand("STOP\n\r"));
		Assert.assertFalse(inboundService.isStopCommand("STOP\n\rabc"));
		System.out.println("Completed.");
	}
}
