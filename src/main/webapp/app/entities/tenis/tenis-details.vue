<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tenis">
        <h2 class="jh-entity-heading" data-cy="tenisDetailsHeading">
          <span v-text="$t('reservaNoblesseApp.tenis.detail.title')">Tenis</span> {{ tenis.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('reservaNoblesseApp.tenis.date')">Date</span>
          </dt>
          <dd>
            <span>{{ tenis.date }}</span>
          </dd>
          <dt>
            <span v-text="$t('reservaNoblesseApp.tenis.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ tenis.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('reservaNoblesseApp.tenis.user')">User</span>
          </dt>
          <dd>
            {{ tenis.user ? tenis.user.login : '' }}
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="tenis.id" :to="{ name: 'TenisEdit', params: { tenisId: tenis.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./tenis-details.component.ts"></script>
