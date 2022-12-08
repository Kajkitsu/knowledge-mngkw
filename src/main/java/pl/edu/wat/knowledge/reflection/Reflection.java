package pl.edu.wat.knowledge.reflection;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Reflection {
   private TypeDescription entityDefinition;
   private TypeDescription requestDefinition;
   private TypeDescription responseDefinition;
   private TypeDescription mapperDefinition;
   private TypePool typePool;
   private ByteBuddy byteBuddy;

    public Reflection() {
        this.typePool = TypePool.Default.ofSystemLoader();
        this.byteBuddy = new ByteBuddy();
        this.entityDefinition = typePool.describe("pl.edu.wat.knowledge.entity.Author").resolve();
        this.requestDefinition = typePool.describe("pl.edu.wat.knowledge.dto.AuthorRequest").resolve();
        this.responseDefinition = typePool.describe("pl.edu.wat.knowledge.dto.AuthorResponse").resolve();
        this.mapperDefinition = typePool.describe("pl.edu.wat.knowledge.mapper.AuthorMapper").resolve();

    }

    public static void apply() {
        var ref = new Reflection();
        ref.applyEntity();
        ref.applyRequest();
        ref.applyResponse();
        ref.applyAuthorMapper();
    }

    private void applyAuthorMapper() {
        TypePool typePool = TypePool.Default.ofSystemLoader();
        ByteBuddy byteBuddy = new ByteBuddy();
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(mapperDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .method(named("fillAuthorRequest"))
                .intercept(MethodCall.invoke(setterAuthorEntity())
                        .onArgument(0)
                        .withMethodCall(MethodCall
                                .invoke(getterRequest())
                                .onArgument(1)))
                .method(named("fillAuthor"))
                .intercept(MethodCall.invoke(setterAuthorResponse())
                        .onArgument(0)
                        .withMethodCall(MethodCall
                                .invoke(getterEntity())
                                .onArgument(1)));

        try (var unloadedAuthor = builder.make()) {
            mapperDefinition =  unloadedAuthor.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MethodDescription getterEntity() {
        return entityDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isGetter("rank"))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription setterAuthorResponse() {
        return responseDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isSetter("rank"))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription getterRequest() {
        return requestDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isGetter("rank"))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription setterAuthorEntity() {
        return entityDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isSetter("rank"))
                .stream()
                .findFirst()
                .orElseThrow();
    }


    private void applyResponse() {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(responseDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty("rank", typePool.describe("java.lang.String").resolve());

        try (var unloadedAuthor = builder.make()) {
            responseDefinition = unloadedAuthor.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void applyRequest() {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(requestDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty("rank", typePool.describe("java.lang.String").resolve());

        try (var unloadedAuthor = builder.make()) {
            requestDefinition = unloadedAuthor.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void applyEntity() {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(entityDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty("rank", typePool.describe("java.lang.String").resolve());

        try (var unloadedAuthor = builder.make()) {
            entityDefinition = unloadedAuthor.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
