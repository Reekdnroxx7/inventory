package com.x404.module.basedao;

import javax.persistence.Id;
import java.io.Serializable;

public interface IdEntity {
    @Id
    Serializable getId();
}
