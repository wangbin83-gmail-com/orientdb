package com.orientechnologies.orient.server.jwt.impl;

import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.security.OToken;
import com.orientechnologies.orient.core.metadata.security.OUser;
import com.orientechnologies.orient.core.metadata.security.jwt.OJsonWebToken;
import com.orientechnologies.orient.core.metadata.security.jwt.OJwtHeader;
import com.orientechnologies.orient.core.metadata.security.jwt.OJwtPayload;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Created by emrul on 28/09/2014.
 *
 * @author Emrul Islam <emrul@emrul.com> Copyright 2014 Emrul Islam
 */
public class JsonWebToken implements OJsonWebToken, OToken {

  public final OJwtHeader  header;
  public final OJwtPayload payload;
  private boolean          isVerified;
  private boolean          isValid;

  public JsonWebToken() {
    this(new OrientJwtHeader(), new OrientJwtPayload());
  }

  public JsonWebToken(OJwtHeader header, OJwtPayload payload) {
    isVerified = false;
    isValid = false;
    this.header = header;
    this.payload = payload;
  }

  @Override
  public OJwtHeader getHeader() {
    return header;
  }

  @Override
  public OJwtPayload getPayload() {
    return payload;
  }

  @Override
  public boolean getIsVerified() {
    return isVerified;
  }

  @Override
  public void setIsVerified(boolean verified) {
    this.isVerified = verified;
  }

  @Override
  public boolean getIsValid() {
    return this.isValid;
  }

  @Override
  public void setIsValid(boolean valid) {
    this.isValid = valid;
  }

  @Override
  public String getSubject() {
    return payload.getSubject();
  }

  @Override
  public OUser getUser(ODatabaseDocumentInternal db) {
    String userRid = ((OrientJwtPayload) payload).getUserRid();
    ODocument result;
    result = db.load(new ORecordId(userRid), "roles:1");
    if (!result.getClassName().equals(OUser.CLASS_NAME)) {
      result = null;
    }
    return new OUser(result);

  }
}