package jorge.rv.quizzz.csvloader;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jorge.rv.quizzz.csvloader.EntityCSV;

@Repository
public interface RepositoryCSV extends JpaRepository<EntityCSV, Long> {

}
