package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {
    @PersistenceContext
    EntityManager entityManager;

    public User authenticate(final String username, final String password) throws
            Exception {
        User user = entityManager.find(User.class, username);
        if(user == null){
            throw new Exception("Unknown User with username: \'"+username+"\'");
        }
        if(user.getPassword().equals(User.hashPassword(password))){
            return user;
        }
        throw new Exception("Wrong password was provided");
    }
}

