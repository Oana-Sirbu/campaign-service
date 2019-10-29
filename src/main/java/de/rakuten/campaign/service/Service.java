package de.rakuten.campaign.service;

public interface Service<S> {
  S save(S entity);

  void deleteById(String id);

  S update(S entity);

  S findById(String id);
}
