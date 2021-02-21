package com.pathan.taskmanager.specification;

import com.pathan.taskmanager.model.Task;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Pathan on 14-Feb-21.
 */
@And({
    @Spec(path = "status", params = "status", spec = Equal.class),
    @Spec(path = "project.id", params = "projectId", spec = Equal.class),
    @Spec(path = "dueDate", params = {"fromDate", "toDate"}, spec = Between.class)

})
public interface TaskSpec extends Specification<Task> {

}
