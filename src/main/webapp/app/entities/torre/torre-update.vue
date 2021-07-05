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
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.torre.numero')" for="torre-numero">Numero</label>
            <input
              type="number"
              class="form-control"
              name="numero"
              id="torre-numero"
              data-cy="numero"
              :class="{ valid: !$v.torre.numero.$invalid, invalid: $v.torre.numero.$invalid }"
              v-model.number="$v.torre.numero.$model"
            />
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
