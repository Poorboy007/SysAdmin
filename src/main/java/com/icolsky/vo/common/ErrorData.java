package com.icolsky.vo.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by FuChang Liu
 */
@Data
@AllArgsConstructor
public class ErrorData <T> {
	T t;
	List<String> errorMeg;

}
