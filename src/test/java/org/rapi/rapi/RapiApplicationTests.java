package org.rapi.rapi;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.route.ConstantFragment;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.infrastructure.EndpointPersistenceImpl;
import org.rapi.rapi.application.api.infrastructure.GroupPersistenceImpl;
import org.rapi.rapi.application.api.service.command.CreateCrudGroupCommand;
import org.rapi.rapi.application.api.service.command.CreateInventoryCommand;
import org.rapi.rapi.application.api.service.command.CreateStructureCommand;
import org.rapi.rapi.application.api.service.command.SetStructureForCrudGroupCommand;
import org.rapi.rapi.application.api.service.command.UpdateStructureCommand;
import org.rapi.rapi.application.api.structure.Structure;
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

    private static final ObjectSchema testObjectSchema = ObjectSchema.create()
        .addField("key", StringSchema.create());

    @Test
    void contextLoads() {
    }

    @BeforeAll
    static void setup() {
        System.setProperty("spring.profiles.active", "test");
    }

    @Test
    void testRestfulEndpointPersistence() {
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
        Assertions.assertThat(endpoint).usingRecursiveComparison().isEqualTo(newEndpoint);
    }

    @Test
    void testProjectPersistence() {
        var context = SpringApplication.run(RapiApplication.class);
        var projectPersistence = context.getBean(ProjectPersistenceImpl.class);
        var project = Project.create("test", new Admin(CrewId.create()));
        var member = new Member(CrewId.create());
        project.inviteCrew(member.getCrew());
        project.addParticipantViaInvitation(member);
        projectPersistence.save(project);
        var newProject = projectPersistence.findById(project.getId());
        Assertions.assertThat(project).usingRecursiveComparison().isEqualTo(newProject);
    }

    @Test
    void testGrpcEndpointPersistence() {
        var context = SpringApplication.run(RapiApplication.class);
        var endpoint = GrpcEndpoint.create("test", "test", "test", false, false,
            ObjectSchema.create(), ObjectSchema.create());
        var endpointPersistence = context.getBean(EndpointPersistenceImpl.class);
        endpointPersistence.deleteAll();
        endpointPersistence.saveGrpc(endpoint);
        var newEndpoint = endpointPersistence.findGrpcById(endpoint.getId());
        Assertions.assertThat(endpoint).usingRecursiveComparison().isEqualTo(newEndpoint);
    }

    @Test
    void testCrudGroupPersistence() {
        var context = SpringApplication.run(RapiApplication.class);
        var groupPersistence = context.getBean(GroupPersistenceImpl.class);
        var group = CrudGroup.create();
        groupPersistence.saveCrud(group);
        var newGroup = groupPersistence.findCrudById(group.getId());
        Assertions.assertThat(group).usingRecursiveComparison().isEqualTo(newGroup);
    }

    @Test
    void testCrudGroupSetSource() {
        var context = SpringApplication.run(RapiApplication.class);
        var createInventoryCommand = context.getBean(CreateInventoryCommand.class);
        var createStructureCommand = context.getBean(CreateStructureCommand.class);
        var updateStructureCommand = context.getBean(UpdateStructureCommand.class);
        var createCrudGroupCommand = context.getBean(CreateCrudGroupCommand.class);
        var setStructureForCrudGroupCommand = context.getBean(
            SetStructureForCrudGroupCommand.class);

        var inventory = createInventoryCommand.createInventory();
        var structure = createStructureCommand.createStructureInInventory(inventory.getId());
        updateStructureCommand.updateStructure(
            Structure.fromRaw(structure.getId(), testObjectSchema, "test"), inventory.getId());

        var crudGroup = createCrudGroupCommand.createCrudGroup(inventory.getId());
        setStructureForCrudGroupCommand.setStructureForCrudGroup(crudGroup.getId(),
            structure.getId(), inventory.getId());

    }



}
