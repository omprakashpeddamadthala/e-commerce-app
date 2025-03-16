package com.ms.dlj.orderline;

import org.hibernate.query.SelectionQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Serializable> {


    List<OrderLine> findAllByOrderId(int i);
}
