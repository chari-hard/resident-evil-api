const SCHEMAS = {
    personagens: {
        label: "Personagens",
        singular: "Personagem",
        titleField: "nome",
        fields: [
            { name: "nome", label: "Nome", type: "text", required: true, placeholder: "Ex: Chris Redfield" },
            { name: "situacao", label: "Situação", type: "text", required: true, placeholder: "Ex: Ativo na BSAA" },
            { name: "imagem", label: "URL da Imagem", type: "textarea", required: false, placeholder: "https://..." }
        ]
    },
    sobreviventes: {
        label: "Sobreviventes",
        singular: "Sobrevivente",
        titleField: "nome",
        fields: [
            { name: "nome", label: "Nome", type: "text", required: true, placeholder: "Ex: Sherry Birkin" },
            { name: "incidente", label: "Incidente", type: "text", required: true, placeholder: "Ex: Raccoon City" },
            { name: "imagem", label: "URL da Imagem", type: "textarea", required: false, placeholder: "https://..." }
        ]
    },
    incidentes: {
        label: "Incidentes",
        singular: "Incidente",
        titleField: "nome",
        fields: [
            { name: "nome", label: "Nome do Incidente", type: "text", required: true, placeholder: "Ex: Mansão Spencer" },
            { name: "ano", label: "Ano", type: "text", required: true, placeholder: "Ex: 1998" },
            { name: "imagem", label: "URL da Imagem", type: "textarea", required: false, placeholder: "https://..." }
        ]
    },
    virus: {
        label: "Vírus",
        singular: "Vírus",
        titleField: "nome",
        fields: [
            { name: "nome", label: "Nome", type: "text", required: true, placeholder: "Ex: T-Virus" },
            { name: "anoDeCriacao", label: "Ano de Criação", type: "text", required: true, placeholder: "Ex: 1978" },
            { name: "imagem", label: "URL da Imagem", type: "textarea", required: false, placeholder: "https://..." }
        ]
    },
    criaturas: {
        label: "Criaturas",
        singular: "Criatura",
        titleField: "nome",
        fields: [
            { name: "nome", label: "Nome", type: "text", required: true, placeholder: "Ex: Licker" },
            { name: "virus", label: "Vírus / Agente", type: "text", required: true, placeholder: "Ex: T-Virus" },
            { name: "imagem", label: "URL da Imagem", type: "textarea", required: false, placeholder: "https://..." }
        ]
    }
};

const state = {
    baseUrl: localStorage.getItem("re_bsaa_api_url") || "http://localhost:8080/api",
    entity: "personagens",
    records: [],
    editingId: null,
    loading: false
};

const els = {
    apiBaseUrl: document.getElementById("apiBaseUrl"),
    btnApplyUrl: document.getElementById("btnApplyUrl"),
    tabs: document.getElementById("entityTabs"),
    banner: document.getElementById("feedbackBanner"),
    formTitle: document.getElementById("formTitle"),
    btnCancelEdit: document.getElementById("btnCancelEdit"),
    form: document.getElementById("entityForm"),
    dynamicFields: document.getElementById("dynamicFields"),
    btnSubmit: document.getElementById("btnSubmit"),
    btnClearForm: document.getElementById("btnClearForm"),
    listTitle: document.getElementById("listTitle"),
    recordCount: document.getElementById("recordCount"),
    searchInput: document.getElementById("searchInput"),
    btnRefresh: document.getElementById("btnRefresh"),
    stateContainer: document.getElementById("stateContainer"),
    itemsList: document.getElementById("itemsList")
};

function getEntityUrl(id = "") {
    const cleanBase = state.baseUrl.replace(/\/+$/, "");
    return id !== "" ? `${cleanBase}/${state.entity}/${id}` : `${cleanBase}/${state.entity}`;
}

function showBanner(message, type = "success") {
    els.banner.textContent = message;
    els.banner.className = `banner ${type}`;
}

function hideBanner() {
    els.banner.className = "banner hidden";
    els.banner.textContent = "";
}

function setListState({ loading = false, error = null, count = 0 }) {
    state.loading = loading;
    els.recordCount.textContent = `${count} ${count === 1 ? "item" : "itens"}`;

    if (loading) {
        els.stateContainer.textContent = "Carregando registros da API...";
        els.stateContainer.classList.remove("hidden");
        els.itemsList.classList.add("hidden");
        return;
    }

    if (error) {
        els.stateContainer.textContent = error;
        els.stateContainer.classList.remove("hidden");
        els.itemsList.classList.add("hidden");
        return;
    }

    if (count === 0) {
        els.stateContainer.textContent = "Nenhum registro encontrado.";
        els.stateContainer.classList.remove("hidden");
        els.itemsList.classList.add("hidden");
        return;
    }

    els.stateContainer.classList.add("hidden");
    els.itemsList.classList.remove("hidden");
}

function renderFormFields(values = {}) {
    const schema = SCHEMAS[state.entity];
    els.dynamicFields.innerHTML = "";

    schema.fields.forEach((field) => {
        const wrapper = document.createElement("div");
        wrapper.className = "field";

        const label = document.createElement("label");
        label.setAttribute("for", `field_${field.name}`);
        label.textContent = field.label + (field.required ? " *" : "");

        const val = values[field.name] ?? "";
        let input;

        if (field.type === "textarea") {
            input = document.createElement("textarea");
            input.value = val;
        } else {
            input = document.createElement("input");
            input.type = field.type;
            input.value = val;
        }

        if (field.placeholder) input.placeholder = field.placeholder;
        input.id = `field_${field.name}`;
        input.name = field.name;
        input.required = Boolean(field.required);

        wrapper.appendChild(label);
        wrapper.appendChild(input);
        els.dynamicFields.appendChild(wrapper);
    });
}

function resetFormMode() {
    const schema = SCHEMAS[state.entity];
    state.editingId = null;
    els.formTitle.textContent = `Novo registro — ${schema.label}`;
    els.btnSubmit.textContent = "Cadastrar";
    els.btnCancelEdit.classList.add("hidden");
    renderFormFields({});
}

function startEdit(item) {
    const schema = SCHEMAS[state.entity];
    const recordId = item.id;
    state.editingId = recordId;
    els.formTitle.textContent = `Editando #${recordId} — ${schema.singular}`;
    els.btnSubmit.textContent = "Salvar alterações";
    els.btnCancelEdit.classList.remove("hidden");
    renderFormFields(item);
    window.scrollTo({ top: 0, behavior: "smooth" });
}

function extractFormPayload() {
    const schema = SCHEMAS[state.entity];
    const payload = {};

    for (const field of schema.fields) {
        const el = document.getElementById(`field_${field.name}`);
        const value = typeof el.value === "string" ? el.value.trim() : el.value;

        if (field.required && !value) {
            throw new Error(`Preencha o campo "${field.label}".`);
        }

        payload[field.name] = value === "" ? null : value;
    }

    return payload;
}

function escapeHtml(str) {
    return String(str)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;");
}

function isSafeImageUrl(url) {
    if (!url || typeof url !== "string") return false;
    const trimmed = url.trim();
    return /^https?:\/\//i.test(trimmed) || trimmed.startsWith("/");
}

function renderList() {
    const schema = SCHEMAS[state.entity];
    const term = els.searchInput.value.trim().toLowerCase();

    const filtered = state.records.filter((item) => {
        if (!term) return true;
        return Object.values(item).some((v) => String(v ?? "").toLowerCase().includes(term));
    });

    if (state.loading) return;

    if (filtered.length === 0) {
        setListState({
            count: 0,
            error: term ? `Nenhum resultado para "${els.searchInput.value}".` : null
        });
        return;
    }

    setListState({ count: filtered.length });
    els.itemsList.innerHTML = "";

    filtered.forEach((item) => {
        const id = item.id ?? "-";
        const card = document.createElement("article");
        card.className = "item-card";

        const top = document.createElement("div");

        if (isSafeImageUrl(item.imagem)) {
            const img = document.createElement("img");
            img.className = "item-thumb";
            img.src = item.imagem.trim();
            img.alt = item[schema.titleField] || `Registro #${id}`;
            img.addEventListener("error", () => img.remove());
            top.appendChild(img);
        }

        const body = document.createElement("div");
        body.className = "item-body";

        const title = document.createElement("h3");
        title.className = "item-title";
        title.textContent = item[schema.titleField] || `Registro #${id}`;
        body.appendChild(title);

        const meta = document.createElement("div");
        meta.className = "item-meta";

        const idRow = document.createElement("span");
        idRow.innerHTML = `<strong>ID:</strong> ${escapeHtml(id)}`;
        meta.appendChild(idRow);

        schema.fields.forEach((field) => {
            if (field.name === schema.titleField || field.name === "imagem") return;
            const val = item[field.name];
            if (val !== undefined && val !== null && val !== "") {
                const row = document.createElement("span");
                row.innerHTML = `<strong>${escapeHtml(field.label)}:</strong> ${escapeHtml(val)}`;
                meta.appendChild(row);
            }
        });

        body.appendChild(meta);
        top.appendChild(body);

        const actions = document.createElement("div");
        actions.className = "item-actions";

        const btnEdit = document.createElement("button");
        btnEdit.type = "button";
        btnEdit.className = "btn btn-secondary";
        btnEdit.textContent = "Editar";
        btnEdit.addEventListener("click", () => startEdit(item));

        const btnDelete = document.createElement("button");
        btnDelete.type = "button";
        btnDelete.className = "btn btn-danger";
        btnDelete.textContent = "Excluir";
        btnDelete.addEventListener("click", () => handleDelete(id));

        actions.appendChild(btnEdit);
        actions.appendChild(btnDelete);

        card.appendChild(top);
        card.appendChild(actions);
        els.itemsList.appendChild(card);
    });
}

async function parseErrorResponse(res) {
    try {
        const data = await res.json();
        if (typeof data?.message === "string") return data.message;
        if (typeof data?.error === "string") return data.error;
        if (Array.isArray(data?.errors)) return data.errors.join("; ");
    } catch {
        // resposta sem corpo JSON
    }
    return `Falha na requisição (HTTP ${res.status}).`;
}

async function fetchRecords() {
    setListState({ loading: true });
    try {
        const res = await fetch(getEntityUrl());
        if (!res.ok) {
            const msg = await parseErrorResponse(res);
            setListState({ error: msg, count: 0 });
            return;
        }
        const data = await res.json();
        state.records = Array.isArray(data) ? data : (data.data ?? data.items ?? []);
        renderList();
    } catch {
        setListState({
            error: "Não foi possível conectar à API. Verifique se o servidor está rodando.",
            count: 0
        });
    }
}

async function handleFormSubmit(event) {
    event.preventDefault();
    hideBanner();

    let payload;
    try {
        payload = extractFormPayload();
    } catch (err) {
        showBanner(err.message, "warn");
        return;
    }

    const isEditing = state.editingId !== null;
    const url = isEditing ? getEntityUrl(state.editingId) : getEntityUrl();
    const method = isEditing ? "PUT" : "POST";

    els.btnSubmit.disabled = true;
    try {
        const res = await fetch(url, {
            method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        if (!res.ok) {
            const msg = await parseErrorResponse(res);
            showBanner(msg, "error");
            return;
        }

        showBanner(
            isEditing ? "Registro atualizado com sucesso." : "Registro cadastrado com sucesso.",
            "success"
        );
        resetFormMode();
        await fetchRecords();
    } catch {
        showBanner("Erro de conexão ao enviar dados para a API.", "error");
    } finally {
        els.btnSubmit.disabled = false;
    }
}

async function handleDelete(id) {
    hideBanner();
    try {
        const res = await fetch(getEntityUrl(id), { method: "DELETE" });
        if (!res.ok) {
            const msg = await parseErrorResponse(res);
            showBanner(msg, "error");
            return;
        }
        if (String(state.editingId) === String(id)) {
            resetFormMode();
        }
        showBanner(`Registro #${id} removido.`, "success");
        await fetchRecords();
    } catch {
        showBanner("Erro de conexão ao excluir registro.", "error");
    }
}

function selectEntity(entityName) {
    if (!SCHEMAS[entityName]) return;
    state.entity = entityName;
    els.searchInput.value = "";
    hideBanner();

    document.querySelectorAll(".tab").forEach((tab) => {
        tab.classList.toggle("active", tab.dataset.entity === entityName);
    });

    els.listTitle.textContent = `Registros — ${SCHEMAS[entityName].label}`;
    resetFormMode();
    fetchRecords();
}

els.tabs.addEventListener("click", (e) => {
    const tab = e.target.closest(".tab");
    if (tab) selectEntity(tab.dataset.entity);
});

els.btnApplyUrl.addEventListener("click", () => {
    const nextUrl = els.apiBaseUrl.value.trim();
    if (!nextUrl) return;
    state.baseUrl = nextUrl;
    localStorage.setItem("re_bsaa_api_url", nextUrl);
    showBanner("Base URL atualizada.", "success");
    fetchRecords();
});

els.form.addEventListener("submit", handleFormSubmit);
els.btnCancelEdit.addEventListener("click", resetFormMode);
els.btnClearForm.addEventListener("click", () => renderFormFields({}));
els.btnRefresh.addEventListener("click", fetchRecords);
els.searchInput.addEventListener("input", renderList);

els.apiBaseUrl.value = state.baseUrl;
selectEntity(state.entity);