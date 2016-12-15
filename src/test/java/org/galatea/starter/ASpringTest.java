
package org.galatea.starter;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public abstract class ASpringTest {

  @Autowired
  private ApplicationContext applicationContext;

  public static String readData(final String fileName) throws IOException {
    return IOUtils.toString(ASpringTest.class.getClassLoader().getResourceAsStream(fileName))
        .trim();
  }

  /**
   * The ActiveMQ broker isn't automatically shutdown after each test, so this step ensures we are
   * shutting it down. Otherwise, you may have old mocks injected into the listeners when you run
   * future tests. Subclasses that override this method should make sure to do a super call
   *
   */
  @After
  public void cleanup() {
    try {
      // We can't get a direct handle to active mq, so let's go through the endpoint registry. This
      // will also ensure that the listener containers are shutdown.
      JmsListenerEndpointRegistry bean =
          applicationContext.getBean(JmsListenerEndpointRegistry.class);

      if (bean.isRunning()) {
        log.info("jms registry is running so let's destroy it");
        bean.destroy();
      } else {
        log.info("jms registry is not running so nothing to do");
      }
    } catch (NoSuchBeanDefinitionException nbd) {
      log.info("No need to shutdown jms listener registry since the bean doesn't exist");
    } catch (Exception err) {
      log.info("Could not determine whether or not to shutdown jms listener"
          + " registry since we came across an exception", err);
    }


  }


}