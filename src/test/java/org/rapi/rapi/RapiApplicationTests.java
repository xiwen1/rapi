package org.rapi.rapi;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.route.ConstantFragment;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.infrastructure.EndpointPersistenceImpl;
import org.rapi.rapi.application.api.structure.schema.ObjectSchema;
import org.rapi.rapi.application.api.structure.schema.StringSchema;
import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.infrastructure.ProjectPersistenceImpl;
import org.rapi.rapi.application.project.project.Project;
import org.rapi.rapi.application.project.project.participant.Admin;
import org.rapi.rapi.application.project.project.participant.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;

@SpringBootTest
class RapiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void restfulEndpointRepositoryWriteAndReadTest() {
        var context = SpringApplication.run(RapiApplication.class);
        var requestSchema = ObjectSchema.create()
            .addField("key", StringSchema.create());
        var endpoint = RestfulEndpoint.create(
            "test", "test", Option.some(requestSchema),
            HttpMethod.POST,
            Route.create(ConstantFragment.create("test"), ConstantFragment.create("test")),
            List.of()
        );
        var endpointPersistence = context.getBean(EndpointPersistenceImpl.class);
        endpointPersistence.deleteAll();

        endpointPersistence.saveRestful(endpoint);

        var newEndpoint = endpointPersistence.findRestfulById(endpoint.getId());

        System.out.println("newEndpoint: " + newEndpoint);
    }

    @Test
    void testProjectModelMapper() {
        var context = SpringApplication.run(RapiApplication.class);
        var projectPersistence = context.getBean(ProjectPersistenceImpl.class);
        var project = Project.create("test", new Admin(CrewId.create()));
        var member = new Member(CrewId.create());
        project.inviteCrew(member.getCrew());
        project.addParticipantViaInvitation(member);
        projectPersistence.save(project);
    }

}
