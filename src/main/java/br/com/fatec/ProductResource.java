package br.com.fatec;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    
    @Operation(
        summary = "Criar produto",
        description = "Esse recurso cadastra um novo produto"
    )
    @APIResponse(
        responseCode = "201",
        description = "Produto cadastrado com sucesso"
    )
    @APIResponse(
        responseCode = "400",
        description = "Produto com dados inválidos para o cadastro"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro interno no servidor"
    )
    @Transactional
    @POST
    @Path("/products")
    public Response createProduct(@Valid ProductDTO productDTO) {
        Product product = new Product(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice());
        product.persist();

        return Response.status(Response.Status.CREATED).entity(product).build();
    }

    @Operation(
        summary = "Lista produtos",
        description = "Esse recurso lista todos os produtos"
    )
    @APIResponse(
        responseCode = "200",
        description = "Produtos listados com sucesso"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro interno no servidor"
    )
    @GET
    @Path("/products")
    public Response listProducts() {
        return Response.status(Response.Status.OK).entity(Product.listAll()).build();
    }

    @Operation(
        summary = "Busca produto por id",
        description = "Esse recurso busca um produto específico"
    )
    @APIResponse(
        responseCode = "200",
        description = "Produto encontrado com sucesso"
    )
    @APIResponse(
        responseCode = "404",
        description = "Produto especificado não encontrado"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro interno no servidor"
    )
    @GET
    @Path("/products/{id}")
    public Response fetchProduct(@PathParam("id") Long id) {
        Product product = Product.findById(id);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Não foi possível encontrar o produto com o id informado!").build();
        }

        return Response.status(Response.Status.OK).entity(product).build();
    }

    @Operation(
        summary = "Deletar produto",
        description = "Esse recurso deleta um produto"
    )
    @APIResponse(
        responseCode = "200",
        description = "Produto deletado com sucesso"
    )
    @APIResponse(
        responseCode = "404",
        description = "Produto especificado não encontrado"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro interno no servidor"
    )
    @Transactional
    @DELETE
    @Path("/products/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
        Product product = Product.findById(id);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Não foi possível encontrar o produto com o id informado!").build();
        }

        product.delete();

        return Response.status(Response.Status.OK).build();
    }

    @Operation(
        summary = "Alterar produto",
        description = "Esse recurso altera os dados de um produto"
    )
    @APIResponse(
        responseCode = "200",
        description = "Produto alterado com sucesso"
    )
    @APIResponse(
        responseCode = "400",
        description = "Produto com dados inválidos para atualizar o produto"
    )
    @APIResponse(
        responseCode = "404",
        description = "Produto especificado não encontrado"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro interno no servidor"
    )
    @Transactional
    @PUT
    @Path("/products/{id}")
    public Response updateProduct(@PathParam("id") Long id, @Valid ProductDTO productDTO) {
        Product product = Product.findById(id);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Não foi possível encontrar o produto com o id informado!").build();
        }

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        product.persist();

        return Response.status(Response.Status.OK).entity("Produto atualizado com sucesso!").build();
    }

}
