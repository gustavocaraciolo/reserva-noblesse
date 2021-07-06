<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="reservaNoblesseApp.apartamento.home.createOrEditLabel"
          data-cy="ApartamentoCreateUpdateHeading"
          v-text="$t('reservaNoblesseApp.apartamento.home.createOrEditLabel')"
        >
          Create or edit a Apartamento
        </h2>
        <div>
          <div class="form-group" v-if="apartamento.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="apartamento.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.apartamento.numero')" for="apartamento-numero">Numero</label>
            <input
              type="number"
              class="form-control"
              name="numero"
              id="apartamento-numero"
              data-cy="numero"
              :class="{ valid: !$v.apartamento.numero.$invalid, invalid: $v.apartamento.numero.$invalid }"
              v-model.number="$v.apartamento.numero.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.apartamento.user')" for="apartamento-user">User</label>
            <select class="form-control" id="apartamento-user" data-cy="user" name="user" v-model="apartamento.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="apartamento.user && userOption.id === apartamento.user.id ? apartamento.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.login }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.apartamento.torre')" for="apartamento-torre">Torre</label>
            <select class="form-control" id="apartamento-torre" data-cy="torre" name="torre" v-model="apartamento.torre">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="apartamento.torre && torreOption.id === apartamento.torre.id ? apartamento.torre : torreOption"
                v-for="torreOption in torres"
                :key="torreOption.id"
              >
                {{ torreOption.id }}
              </option>
            </select>
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
            :disabled="$v.apartamento.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./apartamento-update.component.ts"></script>
