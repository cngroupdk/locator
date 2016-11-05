package dk.cngroup.intranet.locator;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    public void employeesRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/employees")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(
                "[" +
                    "{" +
                        "\"employeeGuid\":0," +
                        "\"managerId\":\"dobiasm\"," +
                        "\"firstName\":\"Victor Isaias\"," +
                        "\"lastName\":\"Cano Becerril\"," +
                        "\"abbreviation\":\"VCB\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"cano@cngroup.cz\"," +
                        "\"location\":\"K1@2nd Floor@KR9\"," +
                        "\"detail\":\"\"," +
                        "\"extension\":\"\"," +
                        "\"voIP\":\"494\"," +
                        "\"id\":\"canov\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":1," +
                        "\"managerId\":\"dobiasm\"," +
                        "\"firstName\":\"Tomas\"," +
                        "\"lastName\":\"Kolaci\"," +
                        "\"abbreviation\":\"TKO\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"kolaci@cngroup.cz\"," +
                        "\"location\":\"E13@Ground@20\"," +
                        "\"detail\":\"Corena DK Life*CDROM, SMSW, MAN*, m/jobcard\"," +
                        "\"extension\":\"156\"," +
                        "\"voIP\":\"416\"," +
                        "\"id\":\"kolacit\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":2," +
                        "\"managerId\":\"dobiasm\"," +
                        "\"firstName\":\"Vit\"," +
                        "\"lastName\":\"Koma\"," +
                        "\"abbreviation\":\"VKO\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"koma@cngroup.cz\"," +
                        "\"location\":\"K1@2nd Floor@KR9\"," +
                        "\"detail\":\"Need to add detail\"," +
                        "\"extension\":\"0\"," +
                        "\"voIP\":\"312\"," +
                        "\"id\":\"komav\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":3," +
                        "\"managerId\":\"dobiasm\"," +
                        "\"firstName\":\"Pavla\"," +
                        "\"lastName\":\"Melegova\"," +
                        "\"abbreviation\":\"PME\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"prakovce1@gmail.com\"," +
                        "\"location\":\"KM1@Mezzanine@KR9\"," +
                        "\"detail\":\"\"," +
                        "\"extension\":\"\"," +
                        "\"voIP\":\"393\"," +
                        "\"id\":\"melegovap\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":4," +
                        "\"managerId\":\"dobiasm\"," +
                        "\"firstName\":\"Ecaterina\"," +
                        "\"lastName\":\"Zubataia\"," +
                        "\"abbreviation\":\"EZU\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"ecaterina.zubataia@cngroup.cz\"," +
                        "\"location\":\"K5@2nd Floor@KR9\"," +
                        "\"detail\":\"\"," +
                        "\"extension\":\"\"," +
                        "\"voIP\":\"\"," +
                        "\"id\":\"zubataiae\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":5," +
                        "\"managerId\":\"dobiasm\"," +
                        "\"firstName\":\"Michal\"," +
                        "\"lastName\":\"Pipal\"," +
                        "\"abbreviation\":\"MPP\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"pipal@cngroup.cz\"," +
                        "\"location\":\"207@2nd Floor@20\"," +
                        "\"detail\":\"\"," +
                        "\"extension\":\"\"," +
                        "\"voIP\":\"\"," +
                        "\"id\":\"pipalm\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":6," +
                        "\"managerId\":\"steen_westhn\"," +
                        "\"firstName\":\"Martina\"," +
                        "\"lastName\":\"Volsicka Gombarova\"," +
                        "\"abbreviation\":\"MVG\"," +
                        "\"role\":\"HR\"," +
                        "\"email\":\"volsicka_gombarova@cngroup.cz\"," +
                        "\"location\":\"106@1st Floor@20\"," +
                        "\"detail\":\"CFO\"," +
                        "\"extension\":\"157\"," +
                        "\"voIP\":\"357\"," +
                        "\"id\":\"volsicka_gombarovam\"" +
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
                        "\"managerId\":\"dobiasm\"," +
                        "\"firstName\":\"Victor Isaias\"," +
                        "\"lastName\":\"Cano Becerril\"," +
                        "\"abbreviation\":\"VCB\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"cano@cngroup.cz\"," +
                        "\"location\":\"K1@2nd Floor@KR9\"," +
                        "\"detail\":\"\"," +
                        "\"extension\":\"\"," +
                        "\"voIP\":\"494\"," +
                        "\"id\":\"canov\"" +
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

}
