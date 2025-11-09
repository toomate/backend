package com.toomate.backend.observer;

import com.toomate.backend.model.Insumo;

public interface LoteListener {
    void notificarMudanca(Insumo insumo);
}
