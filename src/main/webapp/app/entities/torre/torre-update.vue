<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="reservaNoblesseApp.torre.home.createOrEditLabel"
          data-cy="TorreCreateUpdateHeading"
          v-text="$t('reservaNoblesseApp.torre.home.createOrEditLabel')"
        >
          Create or edit a Torre
        </h2>
        <div>
          <div class="form-group" v-if="torre.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="torre.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.torre.nome')" for="torre-nome">Nome</label>
            <input
              type="text"
              class="form-control"
              name="nome"
              id="torre-nome"
              data-cy="nome"
              :class="{ valid: !$v.torre.nome.$invalid, invalid: $v.torre.nome.$invalid }"
              v-model="$v.torre.nome.$model"
            />
            <div v-if="$v.torre.nome.$anyDirty && $v.torre.nome.$invalid">
              <small class="form-text text-danger" v-if="!$v.torre.nome.maxLength" v-text="$t('entity.validation.maxlength', { max: 140 })">
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
            :disabled="$v.torre.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./torre-update.component.ts"></script>
