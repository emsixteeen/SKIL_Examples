package ai.skymind.skil.examples.mnist.modelserver.inference;

import org.datavec.image.data.ImageWritable;

import org.datavec.api.util.ClassPathResource;
import org.datavec.image.transform.ImageTransformProcess;
import org.datavec.spark.transform.model.Base64NDArrayBody;
import org.datavec.spark.transform.model.BatchImageRecord;
import org.datavec.spark.transform.model.SingleImageRecord;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.serde.base64.Nd4jBase64;

//import org.datavec.spark.transform.model.*;

//import org.datavec.image.transform.ImageTransformProcess;

import ai.skymind.skil.examples.mnist.modelserver.inference.model.TransformedImage;


import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import java.io.IOException;

/*
import org.apache.commons.io.FileUtils;
import org.datavec.api.util.ClassPathResource;
*/

//import ai.skymind.cdg.api.model.Knn;
import ai.skymind.skil.examples.mnist.modelserver.inference.model.Inference;
//import ai.skymind.cdg.api.model.TransformedArray;
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
    private String transformedArrayEndpoint = "";


//    @Parameter(names="--inference", description="Endpoint for Inference", required=true)
    private String inferenceEndpoint = "";
/*
    @Parameter(names="--type", description="Type of endpoint (multi or single)", required=true)
    private InferenceType inferenceType;
*/
    @Parameter(names="--input", description="image input file", required=true)
    private String inputImageFile = "";



/*
    @Parameter(names="--sequential", description="If this transform a sequential one", required=false)
    private boolean isSequential = false;

    @Parameter(names="--knn", description="Number of K Nearest Neighbors to return", required=false)
    private int knnN = 20;
*/
    @Parameter(names="--418", description="Temp Fix for DataVec#418", required=false)
    private boolean fix418;


    //private ImageSparkTransformServer server = new ImageSparkTransformServer();



    public void run() throws Exception, IOException {
/*

        Unirest.setObjectMapper(new ObjectMapper() {
            private org.nd4j.shade.jackson.databind.ObjectMapper jacksonObjectMapper =
                            new org.nd4j.shade.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        */


        ImageTransformProcess imgTransformProcess = new ImageTransformProcess.Builder().seed(12345)
                        .scaleImageTransform(10).cropImageTransform(5).build();


        
        final File imageFile = new File( inputImageFile );

        if (!imageFile.exists() || !imageFile.isFile()) {
            System.err.format("unable to access file %s\n", inputImageFile);
            System.exit(2);
        } else {


            System.out.println( "Inference for: " + inputImageFile );

        }

        //SingleImageRecord record =
          //              new SingleImageRecord( imageFile.toURI() );

        //Base64NDArrayBody base64returnBytes = imgTransformProcess.toArray( record );
        // https://github.com/deeplearning4j/DataVec/blob/master/datavec-data/datavec-data-image/src/main/java/org/datavec/image/transform/ImageTransformProcess.java#L99
                        ImageWritable img = imgTransformProcess.transformFileUriToInput( imageFile.toURI() );


        INDArray finalRecord = imgTransformProcess.executeArray( img );

        Base64NDArrayBody imgBase64 = new Base64NDArrayBody(Nd4jBase64.base64String( finalRecord ) );

        System.out.println( imgBase64 );  


        System.out.println( "Finished image conversion" );


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
