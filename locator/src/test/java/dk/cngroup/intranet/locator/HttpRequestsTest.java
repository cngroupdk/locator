package dk.cngroup.intranet.locator;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dk.cngroup.intranet.locator.buildingcomponents.Floor;
import dk.cngroup.intranet.locator.buildingcomponents.Room;
import dk.cngroup.intranet.locator.repositories.FloorsRepository;
import dk.cngroup.intranet.locator.repositories.RoomsRepository;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by cano on 20.10.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HttpRequestsTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FloorsRepository floorRepository;

    @Autowired
    private RoomsRepository roomRepository;

    @Test
    public void employeesRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/employees")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(
                "[" +
                    "{" +
                        "\"employeeGuid\":0," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Victor Isaias\"," +
                        "\"lastName\":\"Cano Becerril\"," +
                        "\"abbreviation\":\"VCB\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"cano@cngroup.cz\"," +
                        "\"location\":\"K1@2nd Floor@KR9\"," +
                        "\"detail\":\"\"," +
                        "\"extension\":\"\"," +
                        "\"voIP\":\"494\"," +
                        "\"id\":\"cano\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":1," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Tomas\"," +
                        "\"lastName\":\"Kolaci\"," +
                        "\"abbreviation\":\"TKO\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"kolaci@cngroup.cz\"," +
                        "\"location\":\"E13@Ground@20\"," +
                        "\"detail\":\"Corena DK Life*CDROM, SMSW, MAN*, m/jobcard\"," +
                        "\"extension\":\"156\"," +
                        "\"voIP\":\"416\"," +
                        "\"id\":\"kolaci\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":2," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Vit\"," +
                        "\"lastName\":\"Koma\"," +
                        "\"abbreviation\":\"VKO\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"koma@cngroup.cz\"," +
                        "\"location\":\"K1@2nd Floor@KR9\"," +
                        "\"detail\":\"Need to add detail\"," +
                        "\"extension\":\"0\"," +
                        "\"voIP\":\"312\"," +
                        "\"id\":\"koma\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":3," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Pavla\"," +
                        "\"lastName\":\"Melegova\"," +
                        "\"abbreviation\":\"PME\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"prakovce1@gmail.com\"," +
                        "\"location\":\"KM1@Mezzanine@KR9\"," +
                        "\"detail\":\"\"," +
                        "\"extension\":\"\"," +
                        "\"voIP\":\"393\"," +
                        "\"id\":\"melegova\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":4," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Ecaterina\"," +
                        "\"lastName\":\"Zubataia\"," +
                        "\"abbreviation\":\"EZU\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"ecaterina.zubataia@cngroup.cz\"," +
                        "\"location\":\"K5@2nd Floor@KR9\"," +
                        "\"detail\":\"\"," +
                        "\"extension\":\"\"," +
                        "\"voIP\":\"\"," +
                        "\"id\":\"zubataia\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":5," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Michal\"," +
                        "\"lastName\":\"Pipal\"," +
                        "\"abbreviation\":\"MPP\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"pipal@cngroup.cz\"," +
                        "\"location\":\"207@2nd Floor@20\"," +
                        "\"detail\":\"\"," +
                        "\"extension\":\"\"," +
                        "\"voIP\":\"\"," +
                        "\"id\":\"pipal\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":6," +
                        "\"managerId\":\"steen_westh\"," +
                        "\"firstName\":\"Martina\"," +
                        "\"lastName\":\"Volsicka Gombarova\"," +
                        "\"abbreviation\":\"MVG\"," +
                        "\"role\":\"HR\"," +
                        "\"email\":\"volsicka_gombarova@cngroup.cz\"," +
                        "\"location\":\"106@1st Floor@20\"," +
                        "\"detail\":\"CFO\"," +
                        "\"extension\":\"157\"," +
                        "\"voIP\":\"357\"," +
                        "\"id\":\"volsicka_gombarova\"" +
                    "}" +
                "]"
            ));
  }

    @Test
    public void employeesSingleRequestReturnsVictorCanoJSONData() throws Exception {

        this.mockMvc.perform(get("/employees/Victor Isaias/Cano Becerril")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(
                "{" +
                        "\"employeeGuid\":0," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Victor Isaias\"," +
                        "\"lastName\":\"Cano Becerril\"," +
                        "\"abbreviation\":\"VCB\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"cano@cngroup.cz\"," +
                        "\"location\":\"K1@2nd Floor@KR9\"," +
                        "\"detail\":\"\"," +
                        "\"extension\":\"\"," +
                        "\"voIP\":\"494\"," +
                        "\"id\":\"cano\"" +
                "}"
            ));
  }

    @Test
    public void buildingsRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/buildings")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(
                "[" +
                    "{" +
                    "\"buildingGuid\":0," +
                    "\"buildingId\":\"KR9\"," +
                    "\"type\":\"Office Space\"," +
                    "\"name\":\"Krakovska 9\"," +
                    "\"city\":\"Praha\"," +
                    "\"postalCode\":\"110 00\"," +
                    "\"streetName\":\"Krakovska\"," +
                    "\"streetNumber\":\"583/9\"" +
                    "}," +
                    "{" +
                    "\"buildingGuid\":1," +
                    "\"buildingId\":\"20\"," +
                    "\"type\":\"Headquarters\"," +
                    "\"name\":\"Ve Smeckach 20\"," +
                    "\"city\":\"Praha\"," +
                    "\"postalCode\":\"110 00\"," +
                    "\"streetName\":\"Ve Smeckach\"," +
                    "\"streetNumber\":\"591/20\"" +
                    "}" +
                "]"
            ));
  }

    @Test
    public void buildingsSingleRequestReturnsKrakovskaJSONData() throws Exception {

        this.mockMvc.perform(get("/buildings/KR9")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(
                "{" +
                    "\"buildingGuid\":0," +
                    "\"buildingId\":\"KR9\"," +
                    "\"type\":\"Office Space\"," +
                    "\"name\":\"Krakovska 9\"," +
                    "\"city\":\"Praha\"," +
                    "\"postalCode\":\"110 00\"," +
                    "\"streetName\":\"Krakovska\"," +
                    "\"streetNumber\":\"583/9\"" +
                "}"
            ));
  }

    @Test
    public void floorsRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/floors")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(
                    "[" +
                            "{" +
                            "\"floorId\":0," +
                            "\"floorName\":\"1st Floor\"," +
                            "\"roomsNumber\":5," +
                            "\"floorplanUrl\":\"/floor201.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":1," +
                            "\"floorName\":\"2nd Floor\"," +
                            "\"roomsNumber\":5," +
                            "\"floorplanUrl\":\"/floor202.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":2," +
                            "\"floorName\":\"Ground\"," +
                            "\"roomsNumber\":1," +
                            "\"floorplanUrl\":\"/floor200.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":3," +
                            "\"floorName\":\"Mezzanine\"," +
                            "\"roomsNumber\":4," +
                            "\"floorplanUrl\":\"/floorKR93.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"KR9\"" +
                            "}," +
                            "{" +
                            "\"floorId\":4," +
                            "\"floorName\":\"2nd Floor\"," +
                            "\"roomsNumber\":5," +
                            "\"floorplanUrl\":\"/floorKR92.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"KR9\"" +
                            "}" +
                        "]"
                ));
    }

    @Test
    public void floorsSingleRequestReturns2ndFloorJSONData() throws Exception {

        this.mockMvc.perform(get("/floors/KR9/2nd Floor")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"floorId\":4,\"floorName\":\"2nd Floor\",\"roomsNumber\":5,\"floorplanUrl\":\"/floorKR92.gif\",\"type\":\"General\",\"buildingId\":\"KR9\"}"
                ));
    }

    @Test
    public void roomsRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/rooms")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(
                    "[" +
                        "{" +
                        "\"roomId\":0," +
                        "\"name\":\"205\"," +
                        "\"type\":\"Development\"," +
                        "\"capacity\":8," +
                        "\"assignedPeople\":6," +
                        "\"floorName\":\"2nd Floor\"," +
                        "\"buildingId\":\"20\"," +
                        "\"styleTop\":\"-360px\"," +
                        "\"styleLeft\":\"170px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":1," +
                        "\"name\":\"106\"," +
                        "\"type\":\"Development\"," +
                        "\"capacity\":6," +
                        "\"assignedPeople\":3," +
                        "\"floorName\":\"1st Floor\"," +
                        "\"buildingId\":\"20\"," +
                        "\"styleTop\":\"-270px\"," +
                        "\"styleLeft\":\"170px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":2," +
                        "\"name\":\"K1\"," +
                        "\"type\":\"Development\"," +
                        "\"capacity\":4," +
                        "\"assignedPeople\":4," +
                        "\"floorName\":\"2nd Floor\"," +
                        "\"buildingId\":\"KR9\"," +
                        "\"styleTop\":\"-384px\"," +
                        "\"styleLeft\":\"420px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":3," +
                        "\"name\":\"K5\"," +
                        "\"type\":\"Development\"," +
                        "\"capacity\":7," +
                        "\"assignedPeople\":6," +
                        "\"floorName\":\"2nd Floor\"," +
                        "\"buildingId\":\"KR9\"," +
                        "\"styleTop\":\"-400px\"," +
                        "\"styleLeft\":\"120px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":4," +
                        "\"name\":\"KM1\"," +
                        "\"type\":\"Development\"," +
                        "\"capacity\":9," +
                        "\"assignedPeople\":5," +
                        "\"floorName\":\"Mezzanine\"," +
                        "\"buildingId\":\"KR9\"," +
                        "\"styleTop\":\"-315px\"," +
                        "\"styleLeft\":\"400px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":5," +
                        "\"name\":\"E13\"," +
                        "\"type\":\"Development\"," +
                        "\"capacity\":6," +
                        "\"assignedPeople\":4," +
                        "\"floorName\":\"Ground\"," +
                        "\"buildingId\":\"20\"," +
                        "\"styleTop\":\"-355px\"," +
                        "\"styleLeft\":\"630px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":6," +
                        "\"name\":\"207\"," +
                        "\"type\":\"Development\"," +
                        "\"capacity\":4," +
                        "\"assignedPeople\":3," +
                        "\"floorName\":\"2nd Floor\"," +
                        "\"buildingId\":\"20\"," +
                        "\"styleTop\":\"-179px\"," +
                        "\"styleLeft\":\"149px\"" +
                        "}" +
                    "]"
                ));
    }

    @Test
    public void roomsSingleRequestReturnsRoom106JSONData() throws Exception {

        this.mockMvc.perform(get("/rooms/20/106")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(
                        "{'roomId':1,'name':'106','type':'Development','capacity':6,'assignedPeople':3,'floorName':1st Floor,'buildingId':'20','styleTop':'-270px','styleLeft':'170px'}"
                ));
    }

    @Test
    public void addRoomKM2ToDB() throws Exception {

        int id = (int)roomRepository.count();
        String jsonContent = "{\"roomId\":" + id + ",\"name\":\"KM2\",\"type\":\"Development\",\"capacity\":2,\"assignedPeople\":0,\"floorName\":\"Mezzanine\",\"buildingId\":\"KR9\",\"styleTop\":\"0px\",\"styleLeft\":\"0px\"}";

        this.mockMvc.perform(post("/rooms/new/room").contentType(MediaType.APPLICATION_JSON)
        .content(
            jsonContent
        ))
        .andExpect(status().isOk());

        Room delRoom = new Room();
        delRoom.setRoomId(id);
        delRoom.setName("KM2");
        delRoom.setType("Development");
        delRoom.setCapacity(2);
        delRoom.setAssignedPeople(0);
        delRoom.setFloorName("Mezzanine");
        delRoom.setBuildingId("KR9");
        delRoom.setStyleTop("0px");
        delRoom.setStyleLeft("0px");
        roomRepository.delete(delRoom);

    }

    @Test
    public void addFloor203ToDB() throws Exception {

        int id = (int)floorRepository.count();
        String jsonContent = "{\"floorId\":" + id + ",\"floorName\":\"3rd Floor\",\"roomsNumber\":5,\"floorplanUrl\":\"/floorKR203.gif\",\"type\":\"General\",\"buildingId\":\"20\"}";

        this.mockMvc.perform(post("/floors/new/floor").contentType(MediaType.APPLICATION_JSON)
        .content(
                jsonContent
        ))
        .andExpect(status().isOk());

        Floor floorDel = new Floor();
        floorDel.setFloorId(id);
        floorDel.setFloorName("3rd Floor");
        floorDel.setRoomsNumber(5);
        floorDel.setFloorplanUrl("/floorKR203.gif");
        floorDel.setType("General");
        floorDel.setBuildingId("20");

        floorRepository.delete(floorDel);
    }

    @Test
    public void updateRoomKM1CoordXY() throws Exception {

        String jsonContent = "{\"roomId\":4,\"name\":\"KM1\",\"type\":\"Development\",\"capacity\":9,\"assignedPeople\":5,\"floorName\":\"Mezzanine\",\"buildingId\":\"KR9\",\"styleTop\":\"-320px\",\"styleLeft\":\"410px\"}";

        this.mockMvc.perform(post("/rooms/update/room").contentType(MediaType.APPLICATION_JSON)
                .content(
                        jsonContent
                ))
                .andExpect(status().isOk());

        Room restoreRoom = new Room();
        restoreRoom.setRoomId(4);
        restoreRoom.setName("KM1");
        restoreRoom.setType("Development");
        restoreRoom.setCapacity(9);
        restoreRoom.setAssignedPeople(5);
        restoreRoom.setFloorName("Mezzanine");
        restoreRoom.setBuildingId("KR9");
        restoreRoom.setStyleTop("-315px");
        restoreRoom.setStyleLeft("400px");

        roomRepository.save(restoreRoom);
    }

}
