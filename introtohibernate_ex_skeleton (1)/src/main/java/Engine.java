import antlr.TokenStreamRewriteEngine;
import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Engine implements Runnable{


    private final EntityManager em;
    private BufferedReader bufferReader;

    public Engine(EntityManager em) {
        this.em=em;
        this.bufferReader=new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Select ex number:");

        try{
           int exNum=Integer.parseInt(bufferReader.readLine());
            switch (exNum){

                case 2:exTwo();
                case 3:exThree();
                case 4:exFour();
                case 5:exFive();
                case 6:exSix();
                case 7:exSeven();
                case 8:exEight();
                case 10:exTen();
                case 11:exEleven();
                case 12:exTwelve();
                case 13:exThirteen();
                
                

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exEight() {

    }

    private void exThirteen() throws IOException {
        System.out.println("Enter town name:");
        String townName=bufferReader.readLine();
        Town town=em.createQuery("select t from Town  t " +
                "where t.name=:t_name",Town.class)
                .setParameter("t_name",townName)
                .getSingleResult();

        int affectedRows=removeAddressesByTownId(town.getId());
        em.getTransaction().begin();

        em.remove(town);
        em.getTransaction().commit();
        System.out.printf("%d address in %s is deleted",
                affectedRows,townName);
    }

    private int removeAddressesByTownId(Integer id) {
        em.getTransaction().begin();
        int result=em.createQuery("delete from Address a " +
                "where a.town.id=:p_id")
                .setParameter("p_id",id)
                .executeUpdate();
        em.getTransaction().commit();

        return result;
    }

    private void exTwelve() {
        List<Object[]>rows=em.createNativeQuery("select d.name,max(e.salary) as `m_salary`from departments d " +
                "join employees e on d.department_id = e.department_id " +
                "group by d.name " +
                "having `m_salary`not between 30000 and 70000;")
                .getResultList();
//        rows.forEach(objects -> {
//            System.out.printf("%s %.2f",
//                    employee.getDepartment().getName(),
//                    employee.getSalary())
//                    ;
//        });
    }

    private void exEleven() {

    }

    private void exTen() {
        em.getTransaction().begin();
       int affectedRows =em.createQuery("update  Employee  e " +
                "set e.salary=e.salary*1.2 " +
                "where e.department.id in :ids")
                .setParameter("ids", Set.of(1,2,4,11))
                .executeUpdate();
       em.getTransaction().commit();
        System.out.println(affectedRows);
    }

    private void exSeven() {
        List<Address>addresses=em.createQuery("select  a from Address a " +
                "order by a.employees.size desc ",Address.class)
                .setMaxResults(10)
                .getResultList();
        addresses.forEach(address -> {
            System.out.printf("%s , %s - %d employees%n",
            address.getText(),
                    address.getTown()==null?"Unknown":address.getTown().getName(),
            address.getEmployees().size());
        });

    }

    private void exSix() throws IOException {
        System.out.println("Enter employee last name:");
        String lastName=bufferReader.readLine();

        Employee employee=em.createQuery("select e from Employee e " +
                "where e.lastName=:l_name",Employee.class)
                .setParameter("l_name",lastName)
                .getSingleResult();

        Address address=createAddress("Vitoshka 15");

        em.getTransaction().begin();
        employee.setAddress(address);
        em.getTransaction().commit();
    }

    private Address createAddress(String addressText) {
        Address address=new Address();
        address.setText(addressText);

        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();
        return address;
    }

    private void exFive() {
       em.createQuery("select e from Employee e " +
                "where e.department.name=:d_name " +
                "order by e.salary,e.id",Employee.class)
                .setParameter("d_name","Research and Development")
                .getResultStream()
               .forEach(employee -> {
                   System.out.printf("%s %s from %s - $%.2f%n",
                           employee.getFirstName(),
                           employee.getLastName(),
                           employee.getDepartment().getName(),
                           employee.getSalary());
               });
    }

    private void exFour() {
       em.createQuery("select e from Employee e " +
                "where e.salary>:min_salary",Employee.class)
                .setParameter("min_salary", BigDecimal.valueOf(50000L))
                .getResultStream()
               .map(Employee::getFirstName)
               .forEach(System.out::println);

    }

    private void exThree() throws IOException {
        System.out.println("Enter employee full name:");
        String[] fullName=bufferReader.readLine().split("\\s+");
        String firstName=fullName[0];
        String lastName=fullName[1];


        Long singleResult=em.createQuery("select count(e) from Employee  e " +
               "where  e.firstName=:f_name and e.lastName=:l_name",
               Long.class)
               .setParameter("f_name",firstName)
               .setParameter("l_name",lastName)
               .getSingleResult();
        System.out.println(singleResult==0
        ?"No":"Yes");
    }

    private void exTwo() {
        em.getTransaction().begin();

        Query query=em.createQuery("update Town t " +
                "set t.name=upper(t.name) " +
                "where length(t.name)<=5 ");

        System.out.println(query.executeUpdate());
        em.getTransaction().commit();
    }
}
