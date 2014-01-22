package com.quicktour.repository;

import com.quicktour.entity.Company;
import com.quicktour.entity.Order;
import com.quicktour.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findById(int id);

    @Query("SELECT COUNT(o.id) FROM Order o WHERE Companies_id = ?1")
    long countByCompanyId(int id);

    @Query("SELECT COUNT(o.id) FROM Order o WHERE Users_ID = ?1")
    long countByUserId(int id);

    @Query("SELECT COUNT(o.id) FROM Order o WHERE o.status = ?1")
    long countByStatus(String status);

    @Query("SELECT COUNT(o.id) FROM Order o WHERE Companies_id = ?1 AND o.status = ?2")
    long countByCompanyIdAndStatus(int id, String status);

    @Query("SELECT COUNT(o.id) FROM Order o WHERE Users_ID = ?1 AND o.status = ?2")
    long countByUserIdAndStatus(int id, String status);

    Page<Order> findAll(Pageable pageable);

    @Query("SELECT o FROM Order AS o INNER JOIN o.companyId AS c WHERE c.id = ?1")
    Page<Order> findByCompanyId(int id, Pageable pageable);

    @Query("SELECT o FROM Order AS o INNER JOIN o.companyId AS c WHERE c.id = ?1 AND o.id = ?2")
    Order findByCompanyId(int id, int orderId);

    @Query("SELECT o FROM Order AS o INNER JOIN o.userId AS u WHERE u.id = ?1")
    Page<Order> findByUsersId(int id, Pageable pageable);

    @Query("SELECT o FROM Order AS o INNER JOIN o.userId AS u WHERE u.id = ?1 AND o.id = ?2")
    Order findByUserId(int id, int orderId);

    Page<Order> findByStatus(String status, Pageable pageable);

    @Query("SELECT o FROM Order AS o WHERE o.status IN ('Received', 'Accepted', 'Confirmed')")
    Page<Order> findActiveOrders(Pageable pageable);

    Page<Order> findByStatusAndCompanyId(String status, Company id, Pageable pageable);

    @Query("SELECT o FROM Order AS o INNER JOIN o.companyId AS c WHERE c.id = ?1 AND o.status IN ('Received', 'Accepted', 'Confirmed')")
    Page<Order> findActiveOrdersByCompanyId(int id, Pageable pageable);

    Page<Order> findByStatusAndUserId(String status, User id, Pageable pageable);

    @Query("SELECT o FROM Order AS o INNER JOIN o.userId AS u WHERE u.id = ?1 AND o.status IN ('Received', 'Accepted', 'Confirmed')")
    Page<Order> findActiveOrdersByUserId(int id, Pageable pageable);

    @Query(value = "select avg(orders.vote) as ratio from orders inner join tour ON orders.TourId = tour.TourId where vote > 0 and tour.ToursId = ?1 group by tour.ToursId\n", nativeQuery = true)
    BigDecimal getRatio(int tourId);

    @Query(value = "select count(orders.vote) as ratio from orders inner join tour ON orders.TourId = tour.TourId where vote > 0 and tour.ToursId = ?1 group by tour.ToursId\n", nativeQuery = true)
    BigInteger getRatioCount(int tourId);

    @Query(value = "select avg(orders.vote) as ratio from orders inner join tour ON orders.TourId = tour.TourId where vote > 0 and tour.ToursId = ?1 and orders.users_ID = ?2 group by tour.ToursId\n", nativeQuery = true)
    BigDecimal getRatio(int tourId, int userId);

    @Query(value = "select count(orders.vote) as ratio from orders inner join tour ON orders.TourId = tour.TourId where vote > 0 and tour.ToursId = ?1 and orders.users_ID = ?2 group by tour.ToursId\n", nativeQuery = true)
    BigInteger getRatioCount(int tourId, int userId);
}
