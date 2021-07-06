<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="torre">
        <h2 class="jh-entity-heading" data-cy="torreDetailsHeading">
          <span v-text="$t('reservaNoblesseApp.torre.detail.title')">Torre</span> {{ torre.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('reservaNoblesseApp.torre.numero')">Numero</span>
          </dt>
          <dd>
            <span>{{ torre.numero }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="torre.id" :to="{ name: 'TorreEdit', params: { torreId: torre.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./torre-details.component.ts"></script>
