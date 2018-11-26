package group.ydq.authority.annotion;

import group.ydq.authority.AuthorityConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AuthorityConfigurer.class)
public @interface EnableAuthorityManage {
}
