<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="reservaNoblesseApp.espaco.home.createOrEditLabel"
          data-cy="EspacoCreateUpdateHeading"
          v-text="$t('reservaNoblesseApp.espaco.home.createOrEditLabel')"
        >
          Create or edit a Espaco
        </h2>
        <div>
          <div class="form-group" v-if="espaco.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="espaco.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.espaco.nome')" for="espaco-nome">Nome</label>
            <input
              type="text"
              class="form-control"
              name="nome"
              id="espaco-nome"
              data-cy="nome"
              :class="{ valid: !$v.espaco.nome.$invalid, invalid: $v.espaco.nome.$invalid }"
              v-model="$v.espaco.nome.$model"
            />
            <div v-if="$v.espaco.nome.$anyDirty && $v.espaco.nome.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.espaco.nome.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 140 })"
              >
                This field cannot be longer than 140 characters.
              </small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.espaco.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./espaco-update.component.ts"></script>
