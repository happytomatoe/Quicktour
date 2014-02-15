package com.quicktour.repository;

import com.quicktour.entity.Company;
import com.quicktour.entity.Order;
import com.quicktour.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer> {


    @Query("SELECT COUNT(o.orderId) FROM Order o WHERE  o.company.companyId = ?1")
    long countByCompanyId(int id);

    @Query("SELECT COUNT(o.orderId) FROM Order o WHERE o.user.userId = ?1")
    long countByUserId(int id);

    @Query("SELECT COUNT(o.orderId) FROM Order o WHERE o.status = ?1")
    long countByStatus(Order.Status status);

    @Query("SELECT COUNT(o.orderId) FROM Order o WHERE o.company.companyId = ?1 AND o.status = ?2")
    long countByCompanyIdAndStatus(int id, Order.Status status);

    @Query("SELECT COUNT(o.orderId) FROM Order o WHERE o.user.userId = ?1 AND o.status = ?2")
    long countByUserIdAndStatus(int id, Order.Status status);

    Page<Order> findAll(Pageable pageable);

    @Query("SELECT o FROM Order AS o INNER JOIN o.company AS c WHERE c.companyId = ?1")
    Page<Order> findByCompanyId(int id, Pageable pageable);

    @Query("SELECT o FROM Order AS o INNER JOIN o.company AS c WHERE c.companyId = ?1 AND o.orderId = ?2")
    Order findByCompanyId(int id, int orderId);

    @Query("SELECT o FROM Order AS o INNER JOIN o.user AS u WHERE u.userId = ?1")
    Page<Order> findByUsersId(int id, Pageable pageable);

    @Query("SELECT o FROM Order AS o INNER JOIN o.user AS u WHERE u.userId = ?1 AND o.orderId = ?2")
    Order findByUserId(int id, int orderId);

    Page<Order> findByStatus(Order.Status status, Pageable pageable);

    @Query("SELECT o FROM Order AS o WHERE status IN ('RECEIVED', 'ACCEPTED', 'CONFIRMED')")
    Page<Order> findActiveOrders(Pageable pageable);

    Page<Order> findByStatusAndCompany(Order.Status status, Company id, Pageable pageable);

    Page<Order> findByStatusAndUser(Order.Status status, User user, Pageable pageable);

    @Query("SELECT o FROM Order AS o INNER JOIN o.company AS c WHERE c.companyId = ?1 AND status IN ('RECEIVED', 'ACCEPTED', 'CONFIRMED')")
    Page<Order> findActiveOrdersByCompanyId(int id, Pageable pageable);

    @Query("SELECT o FROM Order AS o INNER JOIN o.user AS u WHERE u.userId = ?1 AND status IN ('RECEIVED', 'ACCEPTED', 'CONFIRMED')")
    Page<Order> findActiveOrdersByUserId(int id, Pageable pageable);


}
