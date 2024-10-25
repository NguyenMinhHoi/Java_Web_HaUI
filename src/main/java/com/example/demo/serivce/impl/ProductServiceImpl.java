package com.example.demo.serivce.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.serivce.ProductService;
import com.example.demo.utils.CommonUtils;
import com.example.demo.utils.Const;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private VariantRepository variantRepository;
    private GroupOptionRepository groupOptionRepository;
    private OptionRepository optionRepository;
    private ReviewRepository reviewRepository;
    private CartRepository cartRepository;

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Product save(Product entity) {
        return entity;
    }

    @Override
    public void createProduct(Product product) {
          Product back =  productRepository.save(product);
          Variant variant = new Variant();
          new OptionProduct();
          variant.getOptions().add(OptionProduct.builder().name(Const.DEFAULT).build());
          variant.setProduct(back);
          variantRepository.save(variant);
    }

    /**
     * Saves variants for a product based on the provided group options.
     * This method creates and saves new variants for a product by combining all possible
     * option combinations from the given group options.
     *
     * @param groupOptions A list of GroupOption objects representing the different option groups for the product.
     * @param contractId The ID of the product (contract) for which variants are being saved.
     *
     * This method doesn't return a value, but it performs the following operations:
     * 1. Updates the product's group options.
     * 2. Creates new variants by combining all possible options from different groups.
     * 3. Saves the newly created variants to the database.
     */
    @Transient
    @Override
    public void saveVariants(List<GroupOption> groupOptions, Long contractId) {
        Product product = productRepository.findById(contractId).get();
        product.setGroupOptions((Set<GroupOption>) groupOptions);
        productRepository.save(product);
    
        GroupOption firstGroup = groupOptions.stream().findFirst().get();
        List<Variant> firstVariants = new ArrayList<>();
        for(OptionProduct optionProduct: firstGroup.getOptions()){
            Variant newVariant = new Variant();
            newVariant.getOptions().add(optionProduct);
            firstVariants.add(newVariant);
        }
        List<Variant> lastVariant = new ArrayList<>();
        for(int i = 0 ; i < groupOptions.size();i++){
            if(i != 0){
                for(int j = 0 ; j < firstVariants.size();j++){
                    Variant newVariant = new Variant();
                    newVariant.setOptions(firstVariants.get(j).getOptions());
                    for(OptionProduct optionProduct: groupOptions.get(i).getOptions()){
                        newVariant.getOptions().add(optionProduct);
                        lastVariant.add(newVariant);
                    }
                }
                firstVariants = lastVariant;
            }
        }
        variantRepository.saveAll(lastVariant);
    }

    @Override
    public Variant getVariantByOption(List<OptionProduct> options) {
        Set<Long> optionIds = options.stream()
                .map(OptionProduct::getId)
                .collect(Collectors.toSet());
        Long variantId = variantRepository.findVariantByExactOptions(optionIds, options.size());
        return variantRepository.findVariantById(variantId);
    }

    @Override
    public void updateVariant(List<Variant> variants) {
        variantRepository.saveAll(variants);
    }

    @Override
    public List<Product> getAllProductByShopId(Long shopId) {
          
        return List.of();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }
    /**
     * Searches for products based on specified criteria and filters the results by price range.
     * 
     * This method first retrieves products matching the given keyword and category from the repository.
     * It then filters these products based on their variants' price range to ensure they fall within
     * the specified minimum and maximum price bounds.
     *
     * @param keyword    The search term to match against product names or descriptions.
     * @param categoryId The ID of the category to search within. Use null to search all categories.
     * @param minPrice   The minimum price for the product variants. Products with all variants below this price are excluded.
     * @param maxPrice   The maximum price for the product variants. Products with all variants above this price are excluded.
     * @return A list of Product objects that match the search criteria and fall within the specified price range.
     */
    @Override
    public List<Product> searchProducts(String keyword, Long categoryId, Double minPrice, Double maxPrice) {
        // Implement logic to search products based on keyword, category, and price range
        List<Product> products = productRepository.searchProducts(keyword, categoryId);
        for (Product product: products){
            List<Variant> variants = variantRepository.findVariantByProductId(product.getId());
            Double highestCost = variants.stream().max(new Comparator<Variant>() {
                @Override
                public int compare(Variant o1, Variant o2) {
                    return (int) (o1.getPrice()-o2.getPrice());
                }
            }).get().getPrice();
            Double lowestCost = variants.stream().min(new Comparator<Variant>() {
                @Override
                public int compare(Variant o1, Variant o2) {
                    return (int) (o1.getPrice()-o2.getPrice());
                }
            }).get().getPrice();
            if(!(minPrice <= lowestCost && highestCost <= maxPrice)){
                products.remove(product);
            };
        }
        return products;
    }
    /**
     * Retrieves a list of featured products, sorted based on merchant status, sales, and ratings.
     * 
     * This method fetches a limited number of featured products from the repository and then
     * sorts them according to the following criteria:
     * 1. Products from royal merchants are prioritized.
     * 2. Products are then sorted by the number of sales in descending order.
     * 3. If sales are equal, products are sorted by their ratings in descending order.
     *
     * @param limit The maximum number of featured products to retrieve.
     * @return A sorted list of featured Product objects, limited to the specified size.
     */
    @Override
    public List<Product> getFeaturedProducts(int limit) {
        // Implement logic to get featured products, possibly based on ratings or sales
        List<Product> productList = productRepository.findFeaturedProducts(Pageable.ofSize(limit));
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if(o1.getMerchant().getIsRoyal()){
                    return 1;
                }else if(o2.getMerchant().getIsRoyal()){
                    return -1;
                }
                return 0;
            }
        });
        return productList;
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId, int page, int size) {
        // Implement logic to get products by category with pagination
        return productRepository.findByCategoryId(categoryId, PageRequest.of(page, size));
    }
    @Override
    public List<Product> getDiscountedProducts(int limit) {
        // Implement logic to get products with active discounts
        List<Product> products = productRepository.findDiscountedProducts(Pageable.ofSize(limit));
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if(o1.getMerchant().getIsRoyal()){
                    return 1;
                }else if(o2.getMerchant().getIsRoyal()){
                    return -1;
                }
                if(o1.getSold() > o2.getSold()){
                    return (int) (o1.getSold()-o2.getSold());
                }
                else if(o1.getSold() == o2.getSold()){
                    return (int) (o1.getRating() - o2.getRating());
                }
                return (int) (o1.getSold()-o2.getSold());
            }
        });
        return products;
    }

        /**
     * Updates the stock quantity of a specific product variant and adjusts the sold count of the product.
     * This method is typically used when processing orders or managing inventory.
     *
     * @param productId      The unique identifier of the product to be updated.
     * @param quantityChange The change in quantity. Positive values indicate a reduction in stock (e.g., for sales),
     *                       while negative values indicate an increase (e.g., for restocking).
     * @param variantId      The unique identifier of the specific product variant to be updated.
     * @throws RuntimeException if the product with the given productId is not found.
     */
    @Override
    public void updateProductStock(Long productId, int quantityChange, Long variantId) {
        Variant variant = variantRepository.findVariantById(variantId);
        variant.setQuantity(variant.getQuantity() - quantityChange);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setSold(product.getSold() + quantityChange);
        productRepository.save(product);
    }

    /**
     * Retrieves paginated and sorted product reviews for a specific product.
     * 
     * This method fetches a page of reviews for the given product ID, sorts them
     * based on the presence of images and review date, and returns the sorted list.
     * 
     * @param productId The unique identifier of the product for which to retrieve reviews.
     * @param page The page number of reviews to retrieve (zero-based).
     * @param size The number of reviews to include per page.
     * @return A sorted List of Review objects for the specified product, paginated
     *         according to the given page and size parameters. Reviews with images
     *         are prioritized, followed by sorting based on review date.
     */
    @Override
    public List<Review> getProductReviews(Long productId, int page, int size) {
        // Implement logic to get product reviews with pagination
        List<Review> reviews = reviewRepository.findByProductId(productId, PageRequest.of(page, size));
        reviews.sort(new Comparator<Review>() {
            @Override
            public int compare(Review o1, Review o2) {
                if(CommonUtils.isEmpty(o1.getImages())){
                    return 1;
                }
                if(CommonUtils.isEmpty(o2.getImages())){
                    return 1;
                }
                return o1.getDate().compareTo( o1.getDate());
            }
        });
        return reviewRepository.findByProductId(productId, PageRequest.of(page, size));
    }

    @Override
    public void addToCart(Long userId, Long productId, int quantity) {

    }
}
