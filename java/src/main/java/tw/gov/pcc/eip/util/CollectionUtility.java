package tw.gov.pcc.eip.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CollectionUtility {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CollectionUtility.class);

	/**
	 * 複製並回傳 List 中的所有物件
	 *
	 * @param list
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static List cloneListElements(List list) {
		List returnList = new ArrayList();
		if (list != null) {
			for (Object object : list) {
				returnList.add(BeanUtility.cloneBean(object));
			}
		}
		return returnList;
	}

	/**
	 * <pre>
	 * 	ex: ['A','B','C','D'] -> [['A','B'],['C','D']]
	 * </pre>
	 * 
	 * @param <T>
	 * @param list
	 * @param splitSize split個數
	 * @return
	 */
	public static <T> List<List<T>> partition(List<T> list, int splitSize) {
		if (list == null) {
			return null;
		}
		final AtomicInteger counter = new AtomicInteger();
		List<List<T>> resultList = new ArrayList<>();
		for (T element : list) {
			if (counter.getAndIncrement() % splitSize == 0) {
				resultList.add(new ArrayList<>());
			}
			resultList.get(resultList.size() - 1).add(element);
		}
		return resultList;
	}

	public static <T> List<T> trans(List<?> list, Class<T> targetClass) {
		return list.stream().map(x -> {
			try {
				T t = targetClass.newInstance();
				BeanUtility.copyProperties(t, x);
				return t;
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("CollectionUtility.trans() - {}", ExceptionUtility.getStackTrace(e));
			}
			return null;
		}).collect(Collectors.toList());
	}
}
