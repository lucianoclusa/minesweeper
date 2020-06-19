package ar.com.lucianoclusa.minesweeper.infrastructure.repository;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = GameEntityDynamoDBRepository.class)
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    private AmazonDynamoDB amazonDynamoDB;

    private List<String> tables = Arrays.asList( "games", "users");

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());

        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
        }

        tables.forEach(table ->  {
            if (!amazonDynamoDB.listTables().getTableNames().contains(table)) {
                createTable(table);
            }
        });
        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

    private void createTable(String tableName){

        CreateTableRequest request = new CreateTableRequest();
        request.setTableName(tableName);

        List<AttributeDefinition> attributes = new ArrayList<>();
        AttributeDefinition id= new AttributeDefinition();
        id.setAttributeName("id");
        id.setAttributeType(ScalarAttributeType.S);
        attributes.add(id);
        request.setAttributeDefinitions(attributes);

        List<KeySchemaElement> keys = new ArrayList<>();
        KeySchemaElement idKey= new KeySchemaElement();
        idKey.setAttributeName("id");
        idKey.setKeyType(KeyType.HASH);
        keys.add(idKey);
        request.setKeySchema(keys);


        ProvisionedThroughput t = new ProvisionedThroughput();
        t.setReadCapacityUnits(2L);
        t.setWriteCapacityUnits(2L);
        request.setProvisionedThroughput(t);

        StreamSpecification ss = new StreamSpecification();
        ss.setStreamEnabled(false);
        request.setStreamSpecification(ss);

        amazonDynamoDB.createTable(request);
    }
}