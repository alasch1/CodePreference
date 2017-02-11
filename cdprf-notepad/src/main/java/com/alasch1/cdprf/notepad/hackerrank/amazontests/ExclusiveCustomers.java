package com.alasch1.cdprf.notepad.hackerrank.amazontests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ExclusiveCustomers {
	interface CustomerPurchase {
		String getCustomerId();
		String getProductCategory();
	}	
	
	static int countExclusiveCustomers(List<CustomerPurchase> customerPurchases) {
		Map<String, Long> customerProductCombinations = customerPurchases
				.stream()
				.collect(
						Collectors.groupingBy(
								p->(p.getCustomerId() + p.getProductCategory()), Collectors.counting()));
		System.out.println("customerProductCombinations:" + customerProductCombinations);
		
//		Map<String, Long> productCounters = customerPurchases
//				.stream()
//				.collect(
//						Collectors.groupingBy(
//								CustomerPurchase::getProductCategory, Collectors.counting()));
//		System.out.println(productCounters);
//		Map.Entry<String, Long> maxProduct = productCounters.entrySet()
//				.stream()
//				.max(
//						Map.Entry.comparingByValue(Long::compareTo))
//				.get();
//		System.out.println(maxProduct);
		Map<String, Long> customerCounters = customerPurchases
				.stream()
				.collect(
						Collectors.groupingBy(
								CustomerPurchase::getCustomerId, Collectors.counting()));
		System.out.println("customerCounters:" + customerCounters);
		Map.Entry<String, Long> maxCustomer = customerCounters.entrySet()
				.stream()
				.max(
						Map.Entry.comparingByValue(Long::compareTo))
				.get();
		System.out.println(maxCustomer);
		
		Map<String, Set<String>> customerCategories = customerPurchases
				.stream()
				.collect(
						Collectors.groupingBy(
								CustomerPurchase::getCustomerId, 
								Collectors.mapping(CustomerPurchase::getProductCategory,
										Collectors.toSet())));
		System.out.println("customerCategories:" + customerCategories);
		
		List<String> exclusiveCustomers = customerCategories.entrySet()
		.stream()
		.filter(
				s -> s.getValue().size()==1)
		.map(s -> s.getKey())
		.collect(Collectors.toList());
		System.out.println("exclusiveCustomers:" + exclusiveCustomers);
		
		return exclusiveCustomers.size();
	}
	
	public static void main(String[] args) throws IOException{
//        Scanner in = new Scanner(System.in);
//        final String fileName = System.getenv("OUTPUT_PATH");
//        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
//        int res;
//        final List<CustomerPurchase> _customerPurchases = new ArrayList<>();
//        while (in.hasNext()) {
//            _customerPurchases.add(_createCustomerPurchase(in.nextLine()));
//        }        
//        
//        res = countExclusiveCustomers(_customerPurchases);
//        bw.write(String.valueOf(res));
//        bw.newLine();
//        
//        bw.close();
		List<String> input = Arrays.asList(
				"Bob Pets",
				"Bob Pets",
				"Alice Pets",
				"Peter Books",
				"Peter Pets",
				"Annie Books"
		);
		final List<CustomerPurchase> _customerPurchases = new ArrayList<>();
		for (String s : input) {
			_customerPurchases.add(_createCustomerPurchase(s));
		}        

		int res = countExclusiveCustomers(_customerPurchases);
		System.out.println("Number of exclusive customers:" + res);
    }
 
    private static CustomerPurchase _createCustomerPurchase(String line) {
        final String[] split = line.split(" ");
        if (split.length != 2) {
            return null;
        }
        return new CustomerPurchase() {
            @Override
            public String getCustomerId() {
                return split[0];
            }
            
            @Override
            public String getProductCategory() {
                return split[1];
            }
        };
    }
}
