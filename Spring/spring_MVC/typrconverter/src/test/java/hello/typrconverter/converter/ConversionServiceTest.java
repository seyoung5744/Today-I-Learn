package hello.typrconverter.converter;

import static org.assertj.core.api.Assertions.assertThat;

import hello.typrconverter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServiceTest {

    @Test
    public void conversionService() {
        // 등록
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // 사용
        assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10);
        assertThat(conversionService.convert(10, String.class)).isEqualTo("10");

        IpPort ipPort = conversionService.convert("127.0.0.1:9000", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 9000));

        String ipPortString = conversionService.convert(new IpPort("127.0.0.1", 9000), String.class);
        assertThat(ipPortString).isEqualTo("127.0.0.1:9000");
    }
}
