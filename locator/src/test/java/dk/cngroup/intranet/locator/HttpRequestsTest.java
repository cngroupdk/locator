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
            .andExpect(content().string(containsString(
                "[" +
                    "{" +
                    "\"employeeGuid\":\"0\"," +
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
                    "\"employeeGuid\":\"1\"," +
                    "\"managerId\":\"dobiasm\"," +
                    "\"firstName\":\"Tomas\"," +
                    "\"lastName\":\"Kolaci\"," +
                    "\"abbreviation\":\"TKO\"," +
                    "\"role\":\"Developer\"," +
                    "\"email\":\"kolaci@cngroup.cz\"," +
                    "\"location\":\"106@1st Floor@20\"," +
                    "\"detail\":\"Corena DK Life*CDROM, SMSW, MAN*, m/jobcard\"," +
                    "\"extension\":\"156\"," +
                    "\"voIP\":\"416\"," +
                    "\"id\":\"kolacit\"" +
                    "}," +
                    "{" +
                    "\"employeeGuid\":\"2\"," +
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
                    "}" +
                "]"
            )));
  }

    @Test
    public void employeesSingleRequestReturnsVictorCanoJSONData() throws Exception {

        this.mockMvc.perform(get("/employees/Victor Isaias/Cano Becerril")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString(
                "{" +
                    "\"employeeGuid\":\"0\"," +
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
            )));
  }

    @Test
    public void buildingsRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/buildings")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString(
                "[" +
                    "{" +
                    "\"buildingGuid\":0," +
                    "\"buildingId\":\"KR9\"," +
                    "\"type\":\"Office Space\"," +
                    "\"name\":\"Krakovska 9\"," +
                    "\"city\":\"Praha\"," +
                    "\"postalCode\":\"110 00\"," +
                    "\"streetName\":\"Krakosva\"," +
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
            )));
  }

    @Test
    public void buildingsSingleRequestReturnsKrakovskaJSONData() throws Exception {

        this.mockMvc.perform(get("/buildings/KR9")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString(
                "{" +
                    "\"buildingGuid\":0," +
                    "\"buildingId\":\"KR9\"," +
                    "\"type\":\"Office Space\"," +
                    "\"name\":\"Krakovska 9\"," +
                    "\"city\":\"Praha\"," +
                    "\"postalCode\":\"110 00\"," +
                    "\"streetName\":\"Krakosva\"," +
                    "\"streetNumber\":\"583/9\"" +
                "}"
            )));
  }

    @Test
    public void floorsRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/floors")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(
                    "[" +
                            "{" +
                            "\"floorId\":0," +
                            "\"floorNumber\":\"1st Floor\"," +
                            "\"roomsNumber\":5," +
                            "\"floorplanUrl\":\"/floor201.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":1," +
                            "\"floorNumber\":\"2nd Floor\"," +
                            "\"roomsNumber\":5," +
                            "\"floorplanUrl\":\"/floor202.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":2," +
                            "\"floorNumber\":\"Ground\"," +
                            "\"roomsNumber\":1," +
                            "\"floorplanUrl\":\"/floor200.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":3," +
                            "\"floorNumber\":\"Mezzanine\"," +
                            "\"roomsNumber\":4," +
                            "\"floorplanUrl\":\"/floorKR93.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"KR9\"" +
                            "}," +
                            "{" +
                            "\"floorId\":4," +
                            "\"floorNumber\":\"2nd Floor\"," +
                            "\"roomsNumber\":5," +
                            "\"floorplanUrl\":\"/floorKR92.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"KR9\"" +
                            "}" +
                        "]"
                )));
    }

    @Test
    public void floorsSingleRequestReturns2ndFloorJSONData() throws Exception {

        this.mockMvc.perform(get("/floors/KR9/2nd Floor")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"floorId\":4,\"floorNumber\":\"2nd Floor\",\"roomsNumber\":5,\"floorplanUrl\":\"/floorKR92.gif\",\"type\":\"General\",\"buildingId\":\"KR9\"}"
                )));
    }

    @Test
    public void roomsRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/rooms")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(
                    "[" +
                        "{" +
                        "\"roomId\":0," +
                        "\"name\":\"205\"," +
                        "\"type\":\"Development\"," +
                        "\"capacity\":8," +
                        "\"assignedPeople\":6," +
                        "\"floorId\":1," +
                        "\"buildingId\":\"20\"," +
                        "\"styleTop\":\"47%\"," +
                        "\"styleLeft\":\"19%\"" +
                        "}," +
                        "{" +
                        "\"roomId\":1," +
                        "\"name\":\"106\"," +
                        "\"type\":\"Development\"," +
                        "\"capacity\":6," +
                        "\"assignedPeople\":3," +
                        "\"floorId\":0," +
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
                        "\"floorId\":4," +
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
                        "\"floorId\":4," +
                        "\"buildingId\":\"KR9\"," +
                        "\"styleTop\":\"43%\"," +
                        "\"styleLeft\":\"13%\"" +
                        "}," +
                        "{" +
                        "\"roomId\":4," +
                        "\"name\":\"KM1\"," +
                        "\"type\":\"Development\"," +
                        "\"capacity\":9," +
                        "\"assignedPeople\":5," +
                        "\"floorId\":3," +
                        "\"buildingId\":\"KR9\"," +
                        "\"styleTop\":\"60%\"," +
                        "\"styleLeft\":\"38%\"" +
                        "}" +
                    "]"
                )));
    }

    @Test
    public void roomsSingleRequestReturnsRoom106JSONData() throws Exception {

        this.mockMvc.perform(get("/rooms/20/106")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"roomId\":1,\"name\":\"106\",\"type\":\"Development\",\"capacity\":6,\"assignedPeople\":3,\"floorId\":0,\"buildingId\":\"20\",\"styleTop\":\"-270px\",\"styleLeft\":\"170px\"}"
                )));
    }

}
