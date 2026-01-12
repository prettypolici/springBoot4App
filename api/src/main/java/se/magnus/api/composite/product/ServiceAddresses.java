package se.magnus.api.composite.product;

import lombok.Getter;

@Getter
public class ServiceAddresses {
  private String cmp;
  private String pro;
  private String rev;
  private String rec;

  public ServiceAddresses(String cmp, String pro, String rev, String rec) {
    this.cmp = cmp;
    this.pro = pro;
    this.rev = rev;
    this.rec = rec;
  }

  public ServiceAddresses() {
    this.cmp = null;
    this.pro = null;
    this.rev = null;
    this.rec = null;
  }
}
