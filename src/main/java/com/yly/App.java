package com.yly;

import com.example.tutorial.AddressBookProtos;
import com.google.protobuf.Descriptors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * isInitialized(): checks if all the required fields have been set.
 * toString(): returns a human-readable representation of the message, particularly useful for debugging.
 * mergeFrom(Message other): (builder only) merges the contents of other into this message, overwriting singular scalar fields, merging composite fields, and concatenating repeated fields.
 * clear(): (builder only) clears all the fields back to the empty state.
 * <p>
 * <p>
 * <p>
 * byte[] toByteArray();: serializes the message and returns a byte array containing its raw bytes.
 * static Person parseFrom(byte[] data);: parses a message from the given byte array.
 * void writeTo(OutputStream output);: serializes the message and writes it to an OutputStream.
 * static Person parseFrom(InputStream input);: reads and parses a message from an InputStream.
 */
public class App {
    public static void main(String[] args) {
//        basicTest();

        testReverse();
    }

    /**
     * 测试反序列化过程中数据丢失的情况
     * <p>
     * you must not change the tag numbers of any existing fields.
     * you must not add or delete any required fields.
     * you may delete optional or repeated fields.
     * you may add new optional or repeated fields but you must use fresh tag numbers
     * (i.e. tag numbers that were never used in this protocol buffer, not even by deleted fields).
     */
    private static void testReverse() {
//        Request.SearchRequest request = Request.SearchRequest.newBuilder()
//                .setCode(1)
//                .setQuery("new")
//                .build();
//        try (FileOutputStream fo = new FileOutputStream(new File("/home/yuliyang/Desktop/request"))) {
//            request.writeTo(fo);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //从文件中反序列化测试数据变动
        try (FileInputStream fi = new FileInputStream(new File("/home/yuliyang/Desktop/request"))) {

            Request.SearchRequest searchRequest = Request.SearchRequest.parseFrom(fi);
            System.out.println(searchRequest.getCode());
            System.out.println(searchRequest.getQuery());
        } catch (Exception e) {
        }
    }

    private static void basicTest() {
        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder()
                .setId(1000)
                .setName("yuliyang")
                .setEmail("email")
                .addPhones(AddressBookProtos.Person.PhoneNumber.newBuilder()
                        .setNumber("number007")
                        .setType(AddressBookProtos.Person.PhoneType.MOBILE).build())
                .build();

        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder()
                .addPeople(person).build();

        try (FileOutputStream fo = new FileOutputStream(new File("/home/yuliyang/Desktop/seri"))) {
            addressBook.writeTo(fo);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //从文件中反序列化
        try (FileInputStream fi = new FileInputStream(new File("/home/yuliyang/Desktop/seri"))) {
            AddressBookProtos.AddressBook deseriObj = AddressBookProtos.AddressBook
                    .parseFrom(fi);
            for (AddressBookProtos.Person dePerson : deseriObj.getPeopleList()) {
                System.out.println(dePerson.getName());
                System.out.println(dePerson.getEmail());
                System.out.println(dePerson.getId());
                for (AddressBookProtos.Person.PhoneNumber phoneNumber : dePerson.getPhonesList()) {
                    System.out.println(phoneNumber.getNumber());
                    System.out.println(phoneNumber.getType().getNumber());
                }
            }
        } catch (Exception e) {
        }
    }
}
