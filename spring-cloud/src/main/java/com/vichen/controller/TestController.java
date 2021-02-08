package com.vichen.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author chenyu
 * @date 2021/1/27
 */
@RestController
public class TestController {

  @GetMapping("/hello")
  @SentinelResource(value = "test.hello", fallback = "helloError")
  public String apiHello() {
    doBusiness();
    return "Hello!";
  }

  @GetMapping("/err")
  public String apiError() {
    doBusiness();
    return "Oops...";
  }

  @GetMapping("/foo/{id}")
  public String apiFoo(@PathVariable("id") Long id) {
    doBusiness();
    return "Hello " + id;
  }

  @GetMapping("/exclude/{id}")
  public String apiExclude(@PathVariable("id") Long id) {
    doBusiness();
    return "Exclude " + id;
  }

  @GetMapping("/forward")
  public ModelAndView apiForward() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("hello");
    return mav;
  }

  private void doBusiness() {
    Random random = new Random(1);
    try {
      TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public String helloError(String name, Throwable e){
    return "error,"+name;
  }
}
