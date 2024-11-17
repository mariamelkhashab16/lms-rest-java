import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Joined --> normalized schema
@Data // Lombok 
@NoArgsConstructor 
@AllArgsConstructor 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}

enum Role {
    ADMIN,
    PATRON
}
