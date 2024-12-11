package org.rapi.rapi.api;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapi.rapi.RapiApplication;
import org.rapi.rapi.application.api.endpoint.GrpcEndpoint;
import org.rapi.rapi.application.api.endpoint.Response;
import org.rapi.rapi.application.api.endpoint.RestfulEndpoint;
import org.rapi.rapi.application.api.endpoint.route.ConstantFragment;
import org.rapi.rapi.application.api.endpoint.route.Route;
import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.infrastructure.EndpointPersistenceImpl;
import org.rapi.rapi.application.api.infrastructure.GroupPersistenceImpl;
import org.rapi.rapi.application.api.infrastructure.repository.CrudGroupRepository;
import org.rapi.rapi.application.api.infrastructure.repository.InventoryRepository;
import org.rapi.rapi.application.api.infrastructure.repository.JwtGroupRepository;
import org.rapi.rapi.application.api.infrastructure.repository.RestfulEndpointRepository;
import org.rapi.rapi.application.api.infrastructure.repository.StructureRepository;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.command.CreateCrudGroupCommand;
import org.rapi.rapi.application.api.service.command.CreateInventoryCommand;
import org.rapi.rapi.application.api.service.command.CreateJwtGroupCommand;
import org.rapi.rapi.application.api.service.command.CreateRestfulEndpointCommand;
import org.rapi.rapi.application.api.service.command.CreateStructureCommand;
import org.rapi.rapi.application.api.service.command.DeleteRestfulEndpointCommand;
import org.rapi.rapi.application.api.service.command.DeleteStructureCommand;
import org.rapi.rapi.application.api.service.command.SetEndpointsForJwtGroupCommand;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpMethod;

@SpringBootTest
class ApiDomainTests {

    private static final ObjectSchema testObjectSchema = ObjectSchema.create()
        .addField("key", StringSchema.create());

    private static final Route testRoute = Route.create(ConstantFragment.create("test"),
        ConstantFragment.create("test"));

    /**
     * Repositories
     */
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private StructureRepository structureRepository;
    @Autowired
    private RestfulEndpointRepository restfulEndpointRepository;
    @Autowired
    private CrudGroupRepository crudGroupRepository;
    @Autowired
    private JwtGroupRepository jwtGroupRepository;

    /**
     * Commands
     */
    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    private CreateInventoryCommand createInventoryCommand;

    @Autowired
    private CreateStructureCommand createStructureCommand;

    @Autowired
    private UpdateStructureCommand updateStructureCommand;

    @Autowired
    private CreateCrudGroupCommand createCrudGroupCommand;

    @Autowired
    private SetStructureForCrudGroupCommand setStructureForCrudGroupCommand;

    @Autowired
    private DeleteStructureCommand deleteStructureCommand;

    @Autowired
    private CreateRestfulEndpointCommand createRestfulEndpointCommand;

    @Autowired
    private SetEndpointsForJwtGroupCommand setEndpointsForJwtGroupCommand;
    @Autowired
    private CreateJwtGroupCommand createJwtGroupCommand;
    @Autowired
    private DeleteRestfulEndpointCommand deleteRestfulEndpointCommand;


    private RestfulEndpoint createTestRestfulEndpoint(InventoryId inventoryId) {
        return createRestfulEndpointCommand.createRestfulEndpoint("test", "test",
            Option.none(), HttpMethod.GET, testRoute, List.of(
                Response.create()), inventoryId);
    }


    @BeforeAll
    static void setup() {
        System.setProperty("spring.profiles.active", "test");
    }

    @BeforeEach
    void cleanUp() {
        inventoryRepository.deleteAll();
        structureRepository.deleteAll();
        restfulEndpointRepository.deleteAll();
        crudGroupRepository.deleteAll();
        jwtGroupRepository.deleteAll();

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
        var inventory = createInventoryCommand.createInventory();
        var structure = createStructureCommand.createStructureInInventory(inventory.getId());
        updateStructureCommand.updateStructure(
            Structure.fromRaw(structure.getId(), testObjectSchema, "test"), inventory.getId());

        var crudGroup = createCrudGroupCommand.createCrudGroup(inventory.getId());
        setStructureForCrudGroupCommand.setStructureForCrudGroup(crudGroup.getId(),
            structure.getId(), inventory.getId());
    }

    @Test
    void testDeleteStructureWhenActAsCrudGroupSource() {
        inventoryRepository.deleteAll();
        structureRepository.deleteAll();
        restfulEndpointRepository.deleteAll();
        crudGroupRepository.deleteAll();
        var inventory = createInventoryCommand.createInventory();
        var structure = createStructureCommand.createStructureInInventory(inventory.getId());
        updateStructureCommand.updateStructure(
            Structure.fromRaw(structure.getId(), testObjectSchema, "test"), inventory.getId());

        var crudGroup = createCrudGroupCommand.createCrudGroup(inventory.getId());
        setStructureForCrudGroupCommand.setStructureForCrudGroup(crudGroup.getId(),
            structure.getId(), inventory.getId());
        deleteStructureCommand.deleteStructure(inventory.getId(), structure.getId());
    }

    @Test
    void testSetEndpointsForJwtGroup() {
        var inventory = createInventoryCommand.createInventory();
        var endpoint = createTestRestfulEndpoint(inventory.getId());

        var jwtGroup = createJwtGroupCommand.createJwtGroup(inventory.getId());
        setEndpointsForJwtGroupCommand.setEndpointsForJwtGroup(List.of(endpoint.getId()),
            jwtGroup.getId());

    }

    @Test
    void testUpdateStructureWhenActAsCrudGroupSource() {
        var inventory = createInventoryCommand.createInventory();
        var group = createCrudGroupCommand.createCrudGroup(inventory.getId());
        var structure = createStructureCommand.createStructureInInventory(inventory.getId());
        group.set(structure);
        updateStructureCommand.updateStructure(
            Structure.fromRaw(structure.getId(), testObjectSchema, "test"), inventory.getId());
    }

    @Test
    void testDeleteEndpointGeneratedByCrudGroup() {
        var inventory = createInventoryCommand.createInventory();
        var group = createCrudGroupCommand.createCrudGroup(inventory.getId());
        var structure = createStructureCommand.createStructureInInventory(inventory.getId());
        setStructureForCrudGroupCommand.setStructureForCrudGroup(group.getId(),
            structure.getId(), inventory.getId());
        var crudGroupPersist = context.getBean(GroupPersistenceImpl.class);
        var newGroup = crudGroupPersist.findCrudById(group.getId());

        Assertions.assertThatThrownBy(
                () -> deleteRestfulEndpointCommand.deleteRestfulEndpoint(inventory.getId(),
                    newGroup.getDeleteEndpointId().get()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testDeleteEndpointGeneratedByJwtGroup() {
        var inventory = createInventoryCommand.createInventory();
        var endpoint = createTestRestfulEndpoint(inventory.getId());
        var jwtGroup = createJwtGroupCommand.createJwtGroup(inventory.getId());
        var jwtGroupPersist = context.getBean(GroupPersistenceImpl.class);
        var newGroup = jwtGroupPersist.findJwtById(jwtGroup.getId());
        setEndpointsForJwtGroupCommand.setEndpointsForJwtGroup(List.of(endpoint.getId()),
            jwtGroup.getId());
        Assertions.assertThatThrownBy(
                () -> deleteRestfulEndpointCommand.deleteRestfulEndpoint(inventory.getId(),
                    newGroup.getGeneratedEndpoints().get()))
            .isInstanceOf(IllegalArgumentException.class);
    }


}
