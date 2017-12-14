package ai.skymind.skil.examples.mnist.modelserver.inference;

import org.datavec.api.util.ClassPathResource;
import org.datavec.image.transform.ImageTransformProcess;
import org.datavec.spark.transform.model.Base64NDArrayBody;
import org.datavec.spark.transform.model.BatchImageRecord;
import org.datavec.spark.transform.model.SingleImageRecord;


//import ai.skymind.cdg.api.model.Knn;
//import ai.skymind.cdg.api.model.Inference;
import ai.skymind.skil.examples.mnist.modelserver.inference.mode.TransformedImage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
based on
https://github.com/deeplearning4j/DataVec/blob/2995fc72b9148e9510fee77e3e1fc7f7671c6ab1/datavec-spark-inference-parent/datavec-spark-inference-server/src/test/java/org/datavec/spark/transform/ImageSparkTransformServerTest.java



*/
public class MNISTModelServerInferenceExample {


//    @Parameter(names="--transform", description="Endpoint for Transform", required=true)
//    private String transformedArrayEndpoint;

/*
    @Parameter(names="--inference", description="Endpoint for Inference", required=true)
    private String inferenceEndpoint;

    @Parameter(names="--type", description="Type of endpoint (multi or single)", required=true)
    private InferenceType inferenceType;

    @Parameter(names="--input", description="CSV input file", required=true)
    private String inputFile;
*/


/*
    @Parameter(names="--sequential", description="If this transform a sequential one", required=false)
    private boolean isSequential = false;

    @Parameter(names="--knn", description="Number of K Nearest Neighbors to return", required=false)
    private int knnN = 20;

    @Parameter(names="--418", description="Temp Fix for DataVec#418", required=false)
    private boolean fix418;
*/
    public void run() throws Exception {
        /*
        final File file = new File(inputFile);

        if (!file.exists() || !file.isFile()) {
            System.err.format("unable to access file %s\n", inputFile);
            System.exit(2);
        }

        // Open file
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        // Initialize RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Read each line
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");

            // Maybe strip quotes
            for (int i=0; i<fields.length; i++) {
                final String field = fields[i];
                if (field.matches("^\".*\"$")) {
                    fields[i] = field.substring(1, field.length()-1);
                }
            }

            final HttpHeaders requestHeaders = new HttpHeaders();
            final Object transformRequest;

            transformRequest = new TransformedImage.Request(fields);

            if (fix418) {
                // Accept JSON
                requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

                // Temp fix
                List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
                converters.add(new ExtendedMappingJackson2HttpMessageConverter());
                restTemplate.setMessageConverters(converters);
            }

            final HttpEntity<Object> httpEntity =
                    new HttpEntity<Object>(transformRequest, requestHeaders);

            final TransformedImage.Response arrayResponse = restTemplate.postForObject(
                    transformedArrayEndpoint,
                    httpEntity,
                    TransformedImage.Response.class);

            Class clazz;
            Object request;

            if (inferenceType == InferenceType.Single || inferenceType == InferenceType.Multi) {
                clazz = (inferenceType == InferenceType.Single) ?
                        Inference.Response.Classify.class : Inference.Response.MultiClassify.class;

                request = new Inference.Request(arrayResponse.getNdArray());

             } else {
                 clazz = Knn.Response.class;
                 request = new Knn.Request(knnN, arrayResponse.getNdArray());
             }

             final Object response = restTemplate.postForObject(
                         inferenceEndpoint,
                         request,
                         clazz);

             System.out.format("Inference response: %s\n", response.toString());
        }

        br.close();
        */
    }

    public static void main(String[] args) throws Exception {
        MNISTModelServerInferenceExample m = new MNISTModelServerInferenceExample();
        JCommander.newBuilder()
          .addObject(m)
          .build()
          .parse(args);

        m.run();
    }
}

class ExtendedMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public ExtendedMappingJackson2HttpMessageConverter() {
        List<MediaType> types = new ArrayList<MediaType>(super.getSupportedMediaTypes());
        types.add(new MediaType("text", "plain", DEFAULT_CHARSET));
        super.setSupportedMediaTypes(types);
    }
}
