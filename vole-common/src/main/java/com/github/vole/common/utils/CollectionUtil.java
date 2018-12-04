package com.github.vole.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

/**
 * @description 数组集合的公用方法
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CollectionUtil {
	/**
	 * 定义日志
	 */
	private final static Logger logger = LogManager.getLogger(CollectionUtil.class);

	/**
	 * @todo 转换数组类型数据为对象数组,解决原始类型无法强制转换的问题
	 * @param obj
	 * @return
	 */
	public static Object[] convertArray(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Object[])
			return (Object[]) obj;
		if (obj instanceof Collection)
			return ((Collection) obj).toArray();
		// 原始数组类型判断,原始类型直接(Object[])强制转换会发生错误
		if (obj instanceof int[]) {
			int[] tmp = (int[]) obj;
			Integer[] result = new Integer[tmp.length];
			for (int i = 0; i < tmp.length; i++)
				result[i] = tmp[i];
			return result;
		} else if (obj instanceof short[]) {
			short[] tmp = (short[]) obj;
			Short[] result = new Short[tmp.length];
			for (int i = 0; i < tmp.length; i++)
				result[i] = tmp[i];
			return result;
		} else if (obj instanceof long[]) {
			long[] tmp = (long[]) obj;
			Long[] result = new Long[tmp.length];
			for (int i = 0; i < tmp.length; i++)
				result[i] = tmp[i];
			return result;
		} else if (obj instanceof float[]) {
			float[] tmp = (float[]) obj;
			Float[] result = new Float[tmp.length];
			for (int i = 0; i < tmp.length; i++)
				result[i] = tmp[i];
			return result;
		} else if (obj instanceof double[]) {
			double[] tmp = (double[]) obj;
			Double[] result = new Double[tmp.length];
			for (int i = 0; i < tmp.length; i++)
				result[i] = tmp[i];
			return result;
		} else if (obj instanceof boolean[]) {
			boolean[] tmp = (boolean[]) obj;
			Boolean[] result = new Boolean[tmp.length];
			for (int i = 0; i < tmp.length; i++)
				result[i] = tmp[i];
			return result;
		} else if (obj instanceof char[]) {
			char[] tmp = (char[]) obj;
			String[] result = new String[tmp.length];
			for (int i = 0; i < tmp.length; i++)
				result[i] = String.valueOf(tmp[i]);
			return result;
		} else if (obj instanceof byte[]) {
			byte[] tmp = (byte[]) obj;
			Byte[] result = new Byte[tmp.length];
			for (int i = 0; i < tmp.length; i++)
				result[i] = tmp[i];
			return result;
		}
		return new Object[] { obj };
	}

	/**
	 * @todo 数组转换为List集合,此转换只适用于一维和二维数组
	 * @param arySource
	 *            Object
	 * @return List
	 */
	public static List arrayToDeepList(Object arySource) {
		if (null == arySource) {
			System.err.println("arrayToDeepList:the Ary Source is Null");
			return null;
		}
		List resultList = new ArrayList();
		if (arySource instanceof Object[][]) {
			Object[][] aryObject = (Object[][]) arySource;
			if (null != aryObject && 0 < aryObject.length) {
				int rowLength;
				for (int i = 0, n = aryObject.length; i < n; i++) {
					List tmpList = new ArrayList();
					rowLength = aryObject[i].length;
					for (int j = 0; j < rowLength; j++) {
						tmpList.add(aryObject[i][j]);
					}
					resultList.add(tmpList);
				}
			}
		} else {
			if (arySource.getClass().isArray()) {
				Object[] aryObject = convertArray(arySource);
				if (null != aryObject && 0 < aryObject.length) {
					for (int i = 0, n = aryObject.length; i < n; i++)
						resultList.add(aryObject[i]);
				}
			} else {
				logger.error("error define the Array! please sure the array is one or two dimension!");
			}
		}
		return resultList;
	}

	/**
	 * @todo 此转换只适用于一维数组(建议使用Arrays.asList())
	 * @param arySource
	 *            Object
	 * @return List
	 */
	public static List arrayToList(Object arySource) {
		if (null == arySource) {
			System.err.println("arrayToList:the Ary Source is Null");
			return null;
		}
		if (arySource instanceof List)
			return (List) arySource;
		List resultList = new ArrayList();
		if (arySource.getClass().isArray()) {
			Object[] aryObject = convertArray(arySource);
			// return Arrays.asList(aryObject);
			if (null != aryObject && 0 < aryObject.length) {
				for (int i = 0, n = aryObject.length; i < n; i++)
					resultList.add(aryObject[i]);
			}
		} else {
			logger.warn("arySource is not Array! it type is :" + arySource.getClass());
			resultList.add(arySource);
		}
		return resultList;
	}

	/**
	 * 此方法不建议使用，请用Collections中的排序
	 * 
	 * @todo 对简单对象进行排序
	 * @param aryData
	 * @param descend
	 */
	public static void sortArray(Object[] aryData, boolean descend) {
		if (aryData != null && aryData.length > 1) {
			int length = aryData.length;
			Object iData;
			Object jData;
			// 1:string,2:数字;3:日期
			Integer dataType = 1;
			if (aryData[0] instanceof Date)
				dataType = 3;
			else if (aryData[0] instanceof Number)
				dataType = 2;
			boolean lessThen = false;
			for (int i = 0; i < length - 1; i++) {
				for (int j = i + 1; j < length; j++) {
					iData = aryData[i];
					jData = aryData[j];
					if (dataType == 2) {
						lessThen = ((Number) iData).doubleValue() < ((Number) jData).doubleValue();
					} else if (dataType == 3) {
						lessThen = ((Date) iData).before((Date) jData);
					} else
						lessThen = (iData.toString()).compareTo(jData.toString()) < 0;
					// 小于
					if ((descend && lessThen) || (!descend && !lessThen)) {
						aryData[i] = jData;
						aryData[j] = iData;
					}
				}
			}
		}
	}

	/**
	 * @todo 剔除对象数组中的部分数据,简单采用List remove方式实现
	 * @param sourceAry
	 * @param begin
	 * @param length
	 * @return
	 */
	public static Object[] subtractArray(Object[] sourceAry, int begin, int length) {
		if (sourceAry == null || sourceAry.length == 0)
			return null;
		if (begin + length > sourceAry.length || length == 0)
			return sourceAry;
		Object[] distinctAry = new Object[sourceAry.length - length];
		if (begin == 0)
			System.arraycopy(sourceAry, length, distinctAry, 0, sourceAry.length - length);
		else {
			System.arraycopy(sourceAry, 0, distinctAry, 0, begin);
			System.arraycopy(sourceAry, begin + length, distinctAry, begin, sourceAry.length - length - begin);
		}
		return distinctAry;
	}

	/**
	 * @todo 二维list转换为数组对象
	 * @param source
	 * @return
	 */
	public static Object[][] twoDimenlistToArray(Collection source) {
		if (source == null || source.isEmpty())
			return null;
		Object[][] result = new Object[source.size()][];
		int index = 0;
		Object obj;
		for (Iterator iter = source.iterator(); iter.hasNext();) {
			obj = iter.next();
			if (obj instanceof Collection)
				result[index] = ((Collection) obj).toArray();
			else if (obj.getClass().isArray())
				result[index] = convertArray(obj);
			else if (obj instanceof Map)
				result[index] = ((Map) obj).values().toArray();
			index++;
		}
		return result;
	}

	/**
	 * @todo 判断list的维度
	 * @param obj
	 * @return
	 */
	public static int judgeObjectDimen(Object obj) {
		int result = 0;
		if (obj == null)
			return -1;

		if (obj instanceof Collection || obj.getClass().isArray() || obj instanceof Map) {
			result = 1;
			if (obj instanceof Collection) {
				Collection tmp = (Collection) obj;
				if (tmp.isEmpty())
					return result;
				if (((List) obj).get(0) != null && ((List) obj).get(0) instanceof List)
					result = 2;
			} else if (obj.getClass().isArray()) {
				Object[] tmp = convertArray(obj);
				if (tmp.length == 0)
					return result;
				if (tmp[0] != null && tmp[0].getClass().isArray())
					result = 2;
			} else if (obj instanceof Map) {
				Map tmp = (Map) obj;
				if (tmp.isEmpty())
					return result;
				Object setItem = tmp.values().iterator().next();
				if (setItem.getClass().isArray() || setItem instanceof Collection || setItem instanceof Map)
					result = 2;
			}
		}
		return result;
	}

	/**
	 * @todo 将内部的数组转换为list
	 * @param source
	 */
	public static void innerArrayToList(List source) {
		if (source == null || source.isEmpty())
			return;
		if (source.get(0).getClass().isArray()) {
			Object[] rowAry;
			for (int i = 0, n = source.size(); i < n; i++) {
				List rowList = new ArrayList();
				rowAry = convertArray(source.get(i));
				for (int j = 0, k = rowAry.length; j < k; j++)
					rowList.add(rowAry[j]);
				source.remove(i);
				source.add(i, rowList);
			}
		}
	}

	/**
	 * @todo 将内部list转换为数组
	 * @param source
	 * @return
	 */
	public static List innerListToArray(List source) {
		if (source == null || source.isEmpty())
			return source;
		List result = new ArrayList();
		Object sonList;
		for (int i = 0, n = source.size(); i < n; i++) {
			sonList = source.get(i);
			if (null == sonList) {
				result.add(null);
			} else if (sonList instanceof Collection) {
				result.add(((Collection) sonList).toArray());
			} else if (sonList.getClass().isArray()) {
				result.add(sonList);
			} else {
				System.err.println("数据类型必须为Collection");
				break;
			}
		}
		return result;
	}

	/**
	 * @todo 创建汇总行
	 * @param rowSummaryData
	 * @param rowList
	 * @param groupIndex
	 * @param title
	 * @param rowCount
	 * @param radixSize
	 *            小数位长度
	 * @return
	 */
	private static List createSummaryRow(Object[] rowSummaryData, List rowList, int groupIndex, Object[] title,
			int rowCount, int radixSize) {
		List result = new ArrayList();
		List summary = null;
		List average = null;
		int titleIndex = groupIndex;
		if (title.length == 5 && title[4] != null)
			titleIndex = (Integer) title[4];
		// 汇总
		if (title[1] != null || (title[3].equals("left") || title[3].equals("right"))) {
			summary = new ArrayList();
			// 汇总数据加入新的数据行中
			for (int i = 0, n = rowSummaryData.length; i < n; i++)
				summary.add(i, rowSummaryData[i]);
			// 设置分组列前面的数据
			for (int i = 0; i <= titleIndex; i++)
				summary.set(i, rowList.get(i));

			// 设置标题
			if (title[1] != null && !title[1].toString().trim().equals(""))
				summary.set(titleIndex, title[1]);
		}
		// 平均
		if (title[2] != null || (title[3].equals("left") || title[3].equals("right"))) {
			average = new ArrayList();
			// 平均数据加入新的数据行中
			Double averageValue;
			for (int i = 0, n = rowSummaryData.length; i < n; i++) {
				if (rowSummaryData[i] == null)
					average.add(i, null);
				else {
					averageValue = Double.valueOf(rowSummaryData[i].toString().replace(",", "")) / rowCount;
					if (radixSize >= 0)
						average.add(i, BigDecimal.valueOf(averageValue).setScale(radixSize, BigDecimal.ROUND_FLOOR)
								.doubleValue());
					else
						average.add(i, BigDecimal.valueOf(averageValue).doubleValue());
				}
			}
			// 设置分组列前面的数据
			for (int i = 0; i <= titleIndex; i++)
				average.set(i, rowList.get(i));

			// 设置标题
			if (title[2] != null && !title[2].toString().trim().equals(""))
				average.set(titleIndex, title[2]);
		}
		// 汇总或求平均
		if (summary == null || average == null) {
			if (summary != null)
				result.add(summary);
			if (average != null)
				result.add(average);
		} else {
			if (title[3].equals("top") || title[3].equals("bottom")) {
				result.add(summary);
				// 平均数据优先显示
				if (title[3].equals("bottom"))
					result.add(0, average);
			} else {
				// 汇总数据是否左边显示
				boolean isLeft = title[3].equals("left");
				String sumCellValue;
				String averageValue;
				String linkSign = " / ";
				if (title.length == 6 && title[5] != null)
					linkSign = title[5].toString();
				for (int i = 0, n = rowSummaryData.length; i < n; i++) {
					if (rowSummaryData[i] != null) {
						sumCellValue = (summary.get(i) == null) ? "" : summary.get(i).toString();
						averageValue = (average.get(i) == null) ? "" : average.get(i).toString();
						summary.set(i, isLeft ? (sumCellValue + linkSign + averageValue)
								: (averageValue + linkSign + sumCellValue));
					}
				}
				result.add(summary);
			}
		}
		return result;
	}

	/**
	 * @todo 汇总计算
	 * @param groupSumMap
	 * @param rowList
	 * @param summaryColumns
	 * @param radixSize
	 */
	private static void calculateTotal(HashMap groupSumMap, List rowList, Integer[] summaryColumns, int radixSize) {
		Object[] groupSums;
		int size = summaryColumns.length;
		Object cellValue;
		Object sumCellValue;
		int columnIndex;
		// new BigDecimal()
		for (Iterator iter = groupSumMap.values().iterator(); iter.hasNext();) {
			groupSums = (Object[]) iter.next();
			for (int i = 0; i < size; i++) {
				columnIndex = summaryColumns[i];
				sumCellValue = groupSums[columnIndex];
				cellValue = rowList.get(columnIndex);
				if (radixSize >= 0)
					groupSums[columnIndex] = new BigDecimal(
							StringUtil.isBlank(sumCellValue) ? "0" : sumCellValue.toString().replace(",", ""))
									.add(new BigDecimal(StringUtil.isBlank(cellValue) ? "0"
											: cellValue.toString().replace(",", "")))
									.setScale(radixSize, BigDecimal.ROUND_FLOOR);
				else
					groupSums[columnIndex] = new BigDecimal(
							StringUtil.isBlank(sumCellValue) ? "0" : sumCellValue.toString().replace(",", ""))
									.add(new BigDecimal(StringUtil.isBlank(cellValue) ? "0"
											: cellValue.toString().replace(",", "")));
			}
		}
	}

	/**
	 * @todo <b>列转行</b>
	 * @param data
	 * @param colIndex
	 *            保留哪些列进行旋转(其它的列数据忽略)
	 * @return
	 */
	public static List convertColToRow(List data, Integer[] colIndex) {
		if (data == null || data.isEmpty())
			return data;
		boolean innerAry = data.get(0).getClass().isArray();
		int newResultRowCnt = 0;
		if (colIndex == null) {
			newResultRowCnt = innerAry ? convertArray(data.get(0)).length : ((List) data.get(0)).size();
		} else
			newResultRowCnt = colIndex.length;

		/**
		 * 构造结果集
		 */
		Object[][] resultAry = new Object[newResultRowCnt][data.size()];
		Object[] rowAry = null;
		List rowList = null;
		for (int i = 0, n = data.size(); i < n; i++) {
			if (innerAry)
				rowAry = convertArray(data.get(i));
			else
				rowList = (List) data.get(i);
			if (colIndex != null) {
				for (int j = 0, k = colIndex.length; j < k; j++) {
					resultAry[j][i] = innerAry ? rowAry[colIndex[j]] : rowList.get(colIndex[j]);
				}
			} else {
				for (int j = 0; j < newResultRowCnt; j++) {
					resultAry[j][i] = innerAry ? rowAry[j] : rowList.get(j);
				}
			}
		}
		return arrayToDeepList(resultAry);
	}

	public static class SummarySite {
		public static String top = "top";
		public static String bottom = "bottom";
		public static String left = "left";
		public static String right = "right";
	}

	/**
	 * @todo 判断字符串是否在给定的数组中
	 * @param compareStr
	 * @param compareAry
	 * @param ignoreCase
	 * @return
	 */
	public static boolean any(String compareStr, String[] compareAry, boolean ignoreCase) {
		if (compareStr == null || (compareAry == null || compareAry.length == 0))
			return false;
		for (String s : compareAry) {
			if (ignoreCase) {
				if (compareStr.equalsIgnoreCase(s))
					return true;
			} else if (compareStr.equals(s))
				return true;
		}
		return false;
	}
}
