package org.galatea.starter.service;

import java.util.List;
import org.galatea.starter.domain.IexHistoricalPrices;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A Feign Declarative REST Client to access endpoints from the Free and Open IEX API to get market
 * data. See https://iextrading.com/developer/docs/
 */
@FeignClient(name = "IEXToken", url = "${spring.rest.iexBasePathToken}")
public interface IexClientToken {

  /**
   * Get the historical Prices for . See https://iextrading.com/developer/docs/#last.
   *
   * @param symbol symbol to get historical prices for.
   * @param range range of prices to get
   * @param date date for price to get
   *
   * @return a list of the last traded price for each of the symbols passed in.
   */
  @GetMapping("/stock/{symbol}/chart/{range}/{date}?chartByDay=true")
  List<IexHistoricalPrices> getHistoricalPricesByDate(
      @PathVariable("symbol") String symbol,
      @PathVariable("range") String range,
      @PathVariable("date") String date,
      @RequestParam("token") String token);

  /**
   * Get the historical Prices for . See https://iextrading.com/developer/docs/#last.
   *
   * @param symbol symbol to get historical prices for.
   * @param range range of prices to get
   *
   *
   * @return a list of the last traded price for each of the symbols passed in.
   */
  @GetMapping("/stock/{symbol}/chart/{range}")
  List<IexHistoricalPrices> getHistoricalPrices(
      @PathVariable("symbol") String symbol,
      @PathVariable("range") String range,
      @RequestParam("token") String token);

  /**
   * Get the historical Prices for . See https://iextrading.com/developer/docs/#last.
   *
   * @param symbol symbol to get historical prices for.
   *
   * @return a list of the last traded price for each of the symbols passed in.
   */
  @GetMapping("/stock/{symbol}/chart")
  List<IexHistoricalPrices> getHistoricalPricesBySymbol(
      @PathVariable("symbol") String symbol,
      @RequestParam("token") String token);
}