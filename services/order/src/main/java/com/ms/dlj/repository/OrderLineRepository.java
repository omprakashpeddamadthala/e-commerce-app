package com.ms.dlj.repository;

import com.ms.dlj.orderline.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface OrderLineRepository extends JpaRepository<OrderLine, Serializable> {


}
