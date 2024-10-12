package com.eunsun.mssproductapi.concurrent;

import com.eunsun.mssproductapi.domain.price.service.PriceService;
import com.eunsun.mssproductapi.domain.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ConcurrencyTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceService priceService;

    private Long productId;
    private Long categoryId;
    private Long brandId;

    @BeforeEach
    void setUp() {
        productId = 1L;
        categoryId = 1L;
        brandId = 1L;
    }

//    @Test
//    void testUpdateAndPriceComparison() throws InterruptedException {
//        BigDecimal newPrice = new BigDecimal("100");
//        BigDecimal newPrice2 = new BigDecimal("300");
//
//        // 2개의 작업을 동시에 실행하기 위한 CountDownLatch 준비
//        CountDownLatch latch = new CountDownLatch(1);
//        CountDownLatch completionLatch = new CountDownLatch(2); // 두 개의 작업이 완료되면 테스트 완료
//
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//
//        // 1. ProductService.update() 작업을 정의
//        executorService.submit(() -> {
//            try {
//                latch.await(); // 다른 작업이 준비될 때까지 대기
//                assertDoesNotThrow(() -> productService.update(productId, new ProductRequest(brandId, categoryId, "prd1", newPrice ))); // 상품 업데이트
//                log.info("Product updated: " + newPrice);
//            } catch (Exception e) {
//                log.error("ProductService.update error : ", e);
//            } finally {
//                completionLatch.countDown(); // 작업 완료
//            }
//        });
//
//        // 2. PriceService.getCategoryLowestPrices() 작업을 정의
//        executorService.submit(() -> {
//            try {
//                latch.await(); // 다른 작업이 준비될 때까지 대기
//                log.info("priceService Start");
//                Thread.sleep(100);
//
//                LowestPriceCategoryResponse lowestPriceCategoryResponse = priceService.getCategoryLowestPrices(); // 최저 가격 조회
//                log.info("priceService getCategoryLowestPrices");
//                List<LowestPriceCategoryInterface> lowestPriceCategoryInterfaceList = lowestPriceCategoryResponse.lowestPriceCategoryList();
//                BigDecimal price = lowestPriceCategoryInterfaceList.stream().filter(e -> "상의".equals(e.getCategoryNm())).map(LowestPriceCategoryInterface::getPrice).findFirst().orElse(BigDecimal.ZERO);
//                log.info("Category1 Lowest price: " + price);
//            } catch (Exception e) {
//                log.error("PriceService.getCategoryLowestPrices error : ", e);
//            } finally {
//                completionLatch.countDown(); // 작업 완료
//            }
//        });
//
//        executorService.submit(() -> {
//            try {
//                latch.await(); // 다른 작업이 준비될 때까지 대기
//                assertDoesNotThrow(() -> productService.update(productId, new ProductRequest(brandId, categoryId, "prd1", newPrice2 ))); // 상품 업데이트
//                log.info("Product updated: " + newPrice2);
//            } catch (Exception e) {
//                log.error("ProductService.update error : ", e);
//            } finally {
//                completionLatch.countDown(); // 작업 완료
//            }
//        });
//
//        // 모든 작업이 동시에 시작되도록
//        latch.countDown();
//
//        // 작업이 완료될 때까지 대기
//        completionLatch.await(20, TimeUnit.SECONDS);
//
//        executorService.shutdown();
//        System.out.println("Test completed.");
//    }

}
