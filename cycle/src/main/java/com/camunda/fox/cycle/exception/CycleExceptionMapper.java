package com.camunda.fox.cycle.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.camunda.fox.cycle.web.dto.ExceptionDTO;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class CycleExceptionMapper implements ExceptionMapper<CycleException> {

  @Override
  public Response toResponse(CycleException exception) {
    Logger.getLogger(getClass().getSimpleName()).log(Level.SEVERE, "Exception occured during request", exception);
    return Response.serverError().entity(new ExceptionDTO(exception)).build();
  }
}
